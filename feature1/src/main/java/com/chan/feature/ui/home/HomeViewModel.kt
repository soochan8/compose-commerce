package com.chan.feature.ui.home

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.feature.domain.repository.HomeBannerRepository
import com.chan.feature.domain.repository.HomePopularItemRepository
import com.chan.feature.domain.repository.RankingCategoryRepository
import com.chan.feature.ui.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeBannerRepository: HomeBannerRepository,
    private val homePopularItemRepository: HomePopularItemRepository,
    private val rankingCategoryRepository: RankingCategoryRepository
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    init {

    }

    override fun setInitialState() = HomeContract.State(

    )

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            HomeContract.Event.BannerLoad -> getBanners()
            HomeContract.Event.Retry -> getBanners()
            HomeContract.Event.PopularItemLoad -> getPopularItems()
            HomeContract.Event.RankingCategoriesLoad -> getRankingCategories()
        }
    }

    fun getBanners() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            val bannerList = homeBannerRepository.getBanners().map { it.toPresentation() }
            setState { copy(bannerList = bannerList, isLoading = false) }
            setEffect { HomeContract.Effect.ShowToast("배너 로딩") }
        }
    }

    fun getPopularItems() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            val popularItemList =
                homePopularItemRepository.getPopularItemAll().map { it.toPresentation() }
            setState { copy(popularItemList = popularItemList, isLoading = false) }
            setEffect { HomeContract.Effect.ShowToast("배너 로딩") }
        }
    }

    fun getRankingCategories() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            val rankingCategoryList =
                rankingCategoryRepository.getRankingCategories().map { it.toPresentation() }
            setState { copy(rankingCategories = rankingCategoryList, isLoading = false) }
//            setEffect { HomeContract.Effect.ShowToast("배너 로딩") }
        }
    }

}