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
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            val categoryList = categoryRepository.getCategories().map { it.toPresentation() }
            setState { copy(categoryList = categoryList, isLoading = false) }
        }
    }
}