package com.chan.category.ui

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.android.LoadingState
import com.chan.category.domain.CategoryDetailRepository
import com.chan.category.ui.mapper.toProductsModel
import com.chan.category.ui.mapper.toTabsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val categoryDetailRepository: CategoryDetailRepository
) :
    BaseViewModel<CategoryDetailContract.Event, CategoryDetailContract.State, CategoryDetailContract.Effect>() {
    override fun setInitialState() = CategoryDetailContract.State()

    override fun handleEvent(event: CategoryDetailContract.Event) {
        when (event) {
            is CategoryDetailContract.Event.CategoryDetailLoad -> {
                getCategoryTobList(event.categoryId)
                getCategoryDetailList(event.categoryId)
            }

            is CategoryDetailContract.Event.CategoryTabSelected -> {
                getCategoryDetailList(categoryId = event.categoryId)
            }
        }
    }

    private fun getCategoryTobList(categoryId: String) {
        handleRepositoryCall(
            call = {
                categoryDetailRepository.getCategoryDetailTabs(categoryId).map { it.toTabsModel() }
            },
            onSuccess = { categories -> copy(categoryNames = categories) }
        )
    }

    private fun getCategoryDetailList(categoryId: String) {
        setState { copy(selectedCategoryId = categoryId) }

        handleRepositoryCall(
            call = {
                categoryDetailRepository.getCategoryDetailProducts(categoryId)
                    .map { it.toProductsModel() }
            },
            onSuccess = { detailLists -> copy(categoryDetailList = detailLists) }
        )
    }

    private fun <T> handleRepositoryCall(
        call: suspend () -> T,
        onSuccess: CategoryDetailContract.State.(T) -> CategoryDetailContract.State
    ) {
        viewModelScope.launch {
            setState { copy(loadingState = LoadingState.Loading) }
            try {
                val result = call()
                setState { onSuccess(result).copy(loadingState = LoadingState.Success) }
            } catch (e: Exception) {
                setState { copy(loadingState = LoadingState.Error) }
                setEffect { CategoryDetailContract.Effect.ShowError(e.message.toString()) }
            }
        }
    }
}