package com.chan.category.ui

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.android.LoadingState
import com.chan.category.domain.CategoryRepository
import com.chan.category.ui.CategoryContract.Effect.Navigation.*
import com.chan.category.ui.mapper.toCategoryUIModels
import com.chan.category.ui.model.CategoriesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseViewModel<CategoryContract.Event, CategoryContract.State, CategoryContract.Effect>() {

    override fun setInitialState() = CategoryContract.State()

    override fun handleEvent(event: CategoryContract.Event) {
        when (event) {
            is CategoryContract.Event.OnCategorySidebarClick -> {
                updateSelectedCategoryId(event.categoryId)
                onScrollToSidebar(event.categoryId)
                onScrollToContent(event.categoryId)
            }

            is CategoryContract.Event.OnCategoryClick -> setEffect { ToCategoryDetail(event.categoryId) }
            is CategoryContract.Event.OnContentScroll -> handleContentScroll(event.firstVisibleIndex)
        }
    }

    init {
        getAllCategories()
    }

    fun getAllCategories() {
        if (viewState.value.categories.isNotEmpty()) return
        handleRepositoryCall(
            call = {
                categoryRepository.getAllCategories().toCategoryUIModels()
            },
            onSuccess = { categories ->
                val headerPositions = categoryHeaderMapping(categories)
                copy(
                    categories = categories,
                    selectedCategoryId = categories.firstOrNull()?.id,
                    headerPositions = headerPositions
                )
            }
        )
    }

    private fun <T> handleRepositoryCall(
        call: suspend () -> T,
        onSuccess: CategoryContract.State.(T) -> CategoryContract.State,
        onFinally: suspend (T) -> Unit = {}
    ) {
        viewModelScope.launch {
            setState { copy(loadingState = LoadingState.Loading) }

            try {
                val result = call()
                setState { onSuccess(result) }
                onFinally(result)

            } catch (e: Exception) {
                setState { copy(loadingState = LoadingState.Error) }
                setEffect { CategoryContract.Effect.ShowError(e.message.toString()) }
            }
        }
    }


    private fun updateSelectedCategoryId(categoryId: String) {
        setState { copy(selectedCategoryId = categoryId) }
    }

    private fun onScrollToSidebar(categoryId: String) {
        val index = viewState.value.categories.indexOfFirst { it.id == categoryId }
        if (index != -1) {
            setEffect { CategoryContract.Effect.ScrollToSidebar(index) }
        }
    }

    private fun onScrollToContent(categoryId: String) {
        val targetIndex = viewState.value.headerPositions
            .firstOrNull { it.second == categoryId }
            ?.first

        if (targetIndex != null) {
            setEffect { CategoryContract.Effect.ScrollToContent(targetIndex) }
        }
    }

    private fun handleContentScroll(firstVisibleIndex: Int) {
        val newCategoryId = viewState.value.headerPositions
            .filter { it.first <= firstVisibleIndex }
            .maxByOrNull { it.first }
            ?.second

        newCategoryId?.let { id ->
            if (id != viewState.value.selectedCategoryId) {
                updateSelectedCategoryId(id)
            }

            onScrollToSidebar(id)
        }
    }

    private fun categoryHeaderMapping(categories: List<CategoriesModel>): List<Pair<Int, String>> {
        var index = 0
        val positions = mutableListOf<Pair<Int, String>>()

        categories.forEach { category ->
            positions.add(index to category.id)
            index += 1 + category.subCategories.size
        }

        return positions
    }
}