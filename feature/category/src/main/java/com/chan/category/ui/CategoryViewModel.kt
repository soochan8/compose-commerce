package com.chan.category.ui

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.category.domian.CategoryRepository
import com.chan.category.ui.mapper.toPresentation
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
            CategoryContract.Event.CategoriesLoad -> getCategories()
            is CategoryContract.Event.SelectCategory -> updateSelectedCategoryId(event.categoryId)
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            val categoryList = categoryRepository.getCategories().map { it.toPresentation() }
            val firstId = categoryList.firstOrNull()?.id
            setState {
                copy(
                    categoryList = categoryList,
                    selectedCategoryId = firstId,
                    isLoading = false
                )
            }
        }
    }

    private fun updateSelectedCategoryId(categoryId: Int) {
        setState { copy(selectedCategoryId = categoryId) }
    }
}