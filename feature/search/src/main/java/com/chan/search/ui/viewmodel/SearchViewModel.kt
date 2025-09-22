package com.chan.search.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.android.LoadingState
import com.chan.search.domain.model.RankChange
import com.chan.search.domain.model.TrendingSearchVO
import com.chan.search.domain.repository.SearchRepository
import com.chan.search.ui.contract.SearchContract
import com.chan.search.ui.contract.SearchContract.Effect.Navigation.ToBackStack
import com.chan.search.ui.contract.SearchContract.Effect.Navigation.ToCartRoute
import com.chan.search.ui.contract.SearchContract.Effect.Navigation.ToProductDetail
import com.chan.search.ui.mappers.toFilterCategoryModel
import com.chan.search.ui.mappers.toProductsModel
import com.chan.search.ui.mappers.toSearchHistoryModel
import com.chan.search.ui.mappers.toSearchModel
import com.chan.search.ui.mappers.toTrendingSearchModel
import com.chan.search.ui.model.FilterChipType
import com.chan.search.ui.model.SearchResultFilterChipModel
import com.chan.search.ui.model.TrendingSearchModel
import com.chan.search.ui.model.filter.DeliveryOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel<SearchContract.Event, SearchContract.State, SearchContract.Effect>() {

    init {
        observeSearchQuery()
        getRecentSearches()
        getRecommendedKeywords()
        getTrendingSearches()
        setCurrentTime()
        initializeFilterChips()
        initializeCategoryFilters()
    }

    override fun setInitialState() = SearchContract.State()

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewState
            .map { it.search }
            .distinctUntilChanged()
            .debounce(300L)
            .onEach { query ->
                searchProducts(query)
            }
            .launchIn(viewModelScope)
    }

    private fun setCurrentTime() {
        setState { copy(currentTime = getRoundedDownTime()) }
    }

    private fun getRoundedDownTime(): String {
        val now = LocalDateTime.now()
        val roundedMinute = (now.minute / 10) * 10
        val roundedTime = now.withMinute(roundedMinute)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return roundedTime.format(formatter)
    }

    override fun handleEvent(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.OnSearchChanged -> setState {
                copy(
                    search = event.search
                )
            }

            is SearchContract.Event.OnClickSearchProduct -> {
                //클릭 시, 검색어에 맞는 리스트 보여주기
                addSearchKeyword(event.clickedProductName)
                setState { copy(showSearchResult = true) }
            }

            SearchContract.Event.OnClickClearSearch -> setState {
                copy(
                    search = "",
                    searchResults = emptyList(),
                    showSearchResult = false
                )
            }

            is SearchContract.Event.OnAddSearchKeyword -> {
                addSearchKeyword(event.search)
                getSearchResultProducts(event.search)
                setState { copy(showSearchResult = true, search = event.search) }
            }

            is SearchContract.Event.OnRemoveSearchKeyword -> removeSearchKeyword(
                event.search
            )

            SearchContract.Event.OnClearAllRecentSearches -> clearAllSearchKeyword()
            SearchContract.Event.OnSearchTextFocus -> setState { copy(showSearchResult = false) }
            SearchContract.Event.Filter.OnFilterClick -> setState {
                copy(
                    filter = filter.copy(
                        showFilter = !filter.showFilter
                    )
                )
            }

            SearchContract.Event.Filter.OnClear -> handleFilterClear()

            is SearchContract.Event.Filter.OnFilterChipClicked -> handleFilterChipClick(event.chip)
            is SearchContract.Event.Filter.OnDeliveryOptionChanged -> handleDeliveryOptionChange(
                event.option
            )

            is SearchContract.Event.Filter.OnCategoryHeaderClick -> handleCategoryHeaderClick(event.categoryName)
            is SearchContract.Event.Filter.OnSubCategoryClick -> handleSubCategoryClick(event.subCategoryName)
            SearchContract.Event.Filter.OnCategoryClick -> setState {
                copy(
                    filter = filter.copy(
                        isCategorySectionExpanded = !filter.isCategorySectionExpanded
                    )
                )
            }

            is SearchContract.Event.TabRow.OnResultTabSelected -> {
                setState { copy(resultTabRow = resultTabRow.copy(resultSelectedTabIndex = event.index)) }
            }

            SearchContract.Event.OnCartClick -> setEffect { ToCartRoute }
            is SearchContract.Event.OnProductClick -> setEffect { ToProductDetail(event.productId) }
            SearchContract.Event.OnBackStackClick -> setEffect { ToBackStack }
        }
    }

    private fun handleFilterClear() {
        setState {
            copy(
                filter = filter.copy(
                    selectedDeliveryOption = null,
                    expandedCategoryName = null,
                    selectedCategoryIds = emptySet(),
                    isCategorySectionExpanded = false,
                    filterChips = filter.filterChips.map { it.copy(isSelected = false) }
                )
            )
        }
        // 현재 검색어로 검색 결과를 다시 조회
        getSearchResultProducts(viewState.value.search)
    }

    private fun handleSubCategoryClick(subCategoryName: String) {
        val currentSelected = viewState.value.filter.selectedCategoryIds
        val newSelected = if (currentSelected.contains(subCategoryName)) {
            currentSelected - subCategoryName
        } else {
            currentSelected + subCategoryName
        }
        setState { copy(filter = filter.copy(selectedCategoryIds = newSelected)) }
        updateFilteredProductList()
    }

    private fun updateFilteredProductList() {
        viewModelScope.launch {
            val filteredProducts =
                searchRepository.getFilteredProducts(viewState.value.filter.selectedCategoryIds)
                    .map { it.toProductsModel() }
            setState {
                copy(
                    searchResultProducts = filteredProducts,
                    filter = filter.copy(
                        filteredProductCount = filteredProducts.size // 개수도 함께 업데이트
                    )
                )
            }
        }
    }

    private fun handleCategoryHeaderClick(categoryName: String) {
        setState {
            if (this.filter.expandedCategoryName == categoryName) {
                copy(filter = filter.copy(expandedCategoryName = null))
            } else {
                copy(filter = filter.copy(expandedCategoryName = categoryName))
            }
        }
    }

    private fun handleDeliveryOptionChange(option: DeliveryOption) {
        val currentSelectedOption = viewState.value.filter.selectedDeliveryOption
        val newSelectedOption = if (currentSelectedOption == option) {
            null
        } else {
            option
        }

        setState {
            copy(
                filter = filter.copy(
                    selectedDeliveryOption = newSelectedOption,
                    filterChips = filter.filterChips.map { chip ->
                        if (chip.chipType != FilterChipType.TOGGLE) {
                            chip
                        } else {
                            val chipCorrespondsToNewSelection =
                                (chip.text == "오늘드림" && newSelectedOption == DeliveryOption.TODAY_DELIVERY) ||
                                        (chip.text == "픽업" && newSelectedOption == DeliveryOption.PICKUP)

                            chip.copy(isSelected = chipCorrespondsToNewSelection)
                        }
                    }
                )

            )
        }
    }

    private fun handleFilterChipClick(clickedChip: SearchResultFilterChipModel) {
        val isClickedChipAlreadySelected = viewState.value.filter.filterChips
            .find { it.text == clickedChip.text }
            ?.isSelected == true

        val newSelectedOption = if (!isClickedChipAlreadySelected) {
            when (clickedChip.text) {
                "오늘드림" -> DeliveryOption.TODAY_DELIVERY
                "픽업" -> DeliveryOption.PICKUP
                else -> viewState.value.filter.selectedDeliveryOption
            }
        } else {
            null
        }

        setState {
            copy(
                filter = filter.copy(
                    selectedDeliveryOption = newSelectedOption,
                    filterChips = this.filter.filterChips.map { chip ->
                        if (chip.chipType != FilterChipType.TOGGLE) {
                            chip
                        } else {
                            if (chip.text == clickedChip.text) {
                                chip.copy(isSelected = !isClickedChipAlreadySelected)
                            } else {
                                chip.copy(isSelected = false)
                            }
                        }
                    }
                )
            )
        }
    }

    private fun initializeCategoryFilters() {
        viewModelScope.launch {
            val categories = searchRepository.getFilterCategories()
                .map { it.toFilterCategoryModel() }
            setState {
                copy(
                    filter = filter.copy(
                        categoryFilters = categories
                    )
                )
            }
        }
    }

    private fun initializeFilterChips() {
        val filterChips = listOf(
            SearchResultFilterChipModel(
                text = "오늘드림",
                chipType = FilterChipType.TOGGLE
            ),
            SearchResultFilterChipModel(
                text = "픽업",
                chipType = FilterChipType.TOGGLE
            ),
            SearchResultFilterChipModel(
                text = "카테고리",
                chipType = FilterChipType.DROP_DOWN
            ),
            SearchResultFilterChipModel(
                text = "주요기능",
                chipType = FilterChipType.DROP_DOWN
            ),
            SearchResultFilterChipModel(
                text = "가격",
                chipType = FilterChipType.DROP_DOWN
            )

        )
        setState { copy(filter = filter.copy(filterChips = filterChips)) }
    }


    private fun getSearchResultProducts(search: String) {
        handleRepositoryCall(
            call = {
                searchRepository.getSearchResultProducts(search).map { it.toProductsModel() }
            },
            onSuccess = { searchResultProducts ->
                copy(
                    searchResultProducts = searchResultProducts,
                    filter = filter.copy(
                        filteredProductCount = searchResultProducts.size
                    )
                )
            }
        )
    }

    private fun getTrendingSearches() {
        //급상승 검색어 더미 데이터
        val trendingKeywords: List<TrendingSearchModel> = listOf(
            TrendingSearchVO(rank = 1, keyword = "틴트", change = RankChange.UP),
            TrendingSearchVO(rank = 2, keyword = "샴푸", change = RankChange.DOWN),
            TrendingSearchVO(rank = 3, keyword = "기획세트", change = RankChange.NEW),
            TrendingSearchVO(rank = 4, keyword = "선크림", change = RankChange.NONE),
            TrendingSearchVO(rank = 5, keyword = "단백질쉐이크", change = RankChange.NEW),
            TrendingSearchVO(rank = 6, keyword = "넘버즈인", change = RankChange.NONE),
            TrendingSearchVO(rank = 7, keyword = "COLLAGEN", change = RankChange.UP),
            TrendingSearchVO(rank = 8, keyword = "컬러그램 애교살", change = RankChange.NONE),
            TrendingSearchVO(rank = 9, keyword = "모공", change = RankChange.DOWN),
            TrendingSearchVO(rank = 10, keyword = "제이숲", change = RankChange.UP)
        ).map { it.toTrendingSearchModel() }

        setState { copy(trendingSearches = trendingKeywords) }
    }

    private fun getRecommendedKeywords() {
        //추천 키워드 더미 데이터
        val recommendedKeywords: List<String> = listOf(
            "남자 여름 선크림",
            "마스크팩",
            "클렌징폼",
            "립스틱",
            "핸드크림",
            "비타민"
        )
        setState { copy(recommendedKeywords = recommendedKeywords) }
    }

    private fun getRecentSearches() {
        searchRepository.getRecentSearches()
            .map { entities ->
                entities.map { it.toSearchHistoryModel() }
            }
            .onEach { recentSearchResult ->
                setState { copy(recentSearches = recentSearchResult) }
            }
            .launchIn(viewModelScope)
    }

    private fun addSearchKeyword(search: String) {
        if (search.isEmpty()) return
        handleRepositoryCall(
            call = { searchRepository.addSearch(search) }
        )
    }

    private fun removeSearchKeyword(search: String) {
        handleRepositoryCall(
            call = { searchRepository.deleteSearch(search) }
        )
    }

    private fun clearAllSearchKeyword() {
        handleRepositoryCall(
            call = { searchRepository.clearAll() })
    }

    private fun searchProducts(query: String) {
        if (query.isBlank()) {
            setState { copy(searchResults = emptyList()) }
            return
        }

        handleRepositoryCall(
            call = { searchRepository.searchProductName(query).map { it.toSearchModel() } },
            onSuccess = { searchResults -> copy(searchResults = searchResults) }
        )
    }

    private fun <T> handleRepositoryCall(
        call: suspend () -> T,
        onSuccess: SearchContract.State.(T) -> SearchContract.State = { this }
    ) {
        viewModelScope.launch {
            setState { copy(loadingState = LoadingState.Loading) }
            try {
                val result = call()
                setState { onSuccess(result).copy(loadingState = LoadingState.Success) }
            } catch (e: Exception) {
                setState { copy(loadingState = LoadingState.Error) }
                setEffect { SearchContract.Effect.ShowError(e.message.toString()) }
            }
        }
    }
} 