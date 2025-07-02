package com.chan.category.ui

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.category.domian.CategoryRepository
import com.chan.category.ui.mapper.toPresentation
import com.chan.category.ui.model.CategoryModel
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
            is CategoryContract.Event.CategoriesLoad -> getCategories()
            is CategoryContract.Event.SelectCategory -> updateSelectedCategoryId(event.categoryId)
            is CategoryContract.Event.CategoryScrolledIndex -> {
                val newId = viewState.value.headerPositions
                    .filter { it.first <= event.firstVisibleItemIndex }
                    .maxByOrNull { it.first }
                    ?.second
                    ?: viewState.value.selectedCategoryId

                setState { copy(selectedCategoryId = newId) }
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            try {
                val categoryList = categoryRepository.getCategories().map { it.toPresentation() }
                val firstId = categoryList.firstOrNull()?.id

                val mappings = categoryHeaderMapping(categoryList)
                setState {
                    copy(
                        categoryList = categoryList,
                        selectedCategoryId = firstId,
                        headerPositions = mappings,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                setState { copy(isLoading = false, isError = true) }
                setEffect { CategoryContract.Effect.ShowError(e.message.toString()) }
            }
        }
    }

    private fun updateSelectedCategoryId(categoryId: Int) {
        setState { copy(selectedCategoryId = categoryId) }
    }

    private fun categoryHeaderMapping(categories: List<CategoryModel>): List<Pair<Int, Int>> {
        val list = mutableListOf<Pair<Int, Int>>()
        var index = 0
        categories.forEach { category ->
            category.subCategoryItems.forEach { subCategory ->
                //리스트 subCategoryItem.id
                list += index to category.id
                //헤더에 속한 CategoryId
                index += 1 + subCategory.items.size
            }
        }
        return list
    }
}