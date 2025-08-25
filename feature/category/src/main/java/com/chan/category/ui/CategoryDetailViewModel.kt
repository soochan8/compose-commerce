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
                updateSelectedTabId(event.categoryId)
                getCategoryTabList(event.categoryId)
                getProductsByCategoryId(event.categoryId)
            }

            is CategoryDetailContract.Event.CategoryTabSelected -> {
                updateSelectedTabId(event.categoryId)
                getProductsByCategoryId(event.categoryId)
                updateSelectedTabIndex(event.categoryId)
            }

            is CategoryDetailContract.Event.OnProductClick -> {
                setEffect { CategoryDetailContract.Effect.Navigation.ToProductDetail(event.productId) }
            }
        }
    }

    private fun updateSelectedTabId(categoryId: String) {
        setState { copy(selectedCategoryTabId = categoryId) }
    }

    private fun updateSelectedTabIndex(categoryId: String) {
        val selectedIndex =
            viewState.value.categoryNames.indexOfFirst { it.categoryId == categoryId }
                .coerceAtLeast(0)

        setState { copy(selectedCategoryTabIndex = selectedIndex) }

    }

    private fun getCategoryTabList(categoryId: String) {
        handleRepositoryCall(
            call = {
                categoryDetailRepository.getCategoryTabs(categoryId).map { it.toTabsModel() }
            },
            onSuccess = { categories -> copy(categoryNames = categories) },
            onFinally = {
                updateSelectedTabIndex(categoryId)
            }
        )
    }

    private fun getProductsByCategoryId(categoryId: String) {
        handleRepositoryCall(
            call = {
                categoryDetailRepository.getProductsByCategory(categoryId)
                    .map { it.toProductsModel() }
            },
            onSuccess = { productsList ->
                copy(productListByCategory = productsList)
            }
        )
    }

    private fun <T> handleRepositoryCall(
        call: suspend () -> T,
        onSuccess: CategoryDetailContract.State.(T) -> CategoryDetailContract.State,
        onFinally: suspend (T) -> Unit = {}
    ) {
        viewModelScope.launch {
            setState { copy(loadingState = LoadingState.Loading) }
            try {
                val result = call()
                setState { onSuccess(result).copy(loadingState = LoadingState.Success) }
                onFinally(result)
            } catch (e: Exception) {
                setState { copy(loadingState = LoadingState.Error) }
                setEffect { CategoryDetailContract.Effect.ShowError(e.message.toString()) }
            }
        }
    }
}