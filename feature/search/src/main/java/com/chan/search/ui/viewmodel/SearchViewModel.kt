package com.chan.search.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.android.LoadingState
import com.chan.search.domain.model.RankChange
import com.chan.search.domain.model.TrendingSearchVO
import com.chan.search.domain.repository.SearchRepository
import com.chan.search.ui.contract.SearchContract
import com.chan.search.ui.mappers.toProductsModel
import com.chan.search.ui.mappers.toSearchHistoryModel
import com.chan.search.ui.mappers.toSearchModel
import com.chan.search.ui.mappers.toTrendingSearchModel
import com.chan.search.ui.model.TrendingSearchModel
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

            is SearchContract.Event.OnClickSearchResult -> {
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
                setState { copy(showSearchResult = true) }
            }

            is SearchContract.Event.OnRemoveSearchKeyword -> removeSearchKeyword(
                event.search
            )

            SearchContract.Event.OnClearAllRecentSearches -> clearAllSearchKeyword()
        }
    }

    private fun getSearchResultProducts(search: String) {
        handleRepositoryCall(
            call = {
                searchRepository.getSearchResultProducts(search).map { it.toProductsModel() }
            },
            onSuccess = { searchResultProducts -> copy(searchResultProducts = searchResultProducts) }
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
                // 데이터베이스 Entity를 UI 모델로 변환
                entities.map { it.toSearchHistoryModel() }
            }
            .onEach { recentSearchResult ->
                // Flow가 새로운 데이터를 방출할 때마다 상태 업데이트
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