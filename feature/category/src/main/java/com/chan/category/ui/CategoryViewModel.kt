package com.chan.category.ui

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.android.LoadingState
import com.chan.category.domain.CategoryRepository
import com.chan.category.ui.mapper.toCategoryModel
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
        }
    }

    fun getCategories() {
        if (viewState.value.categoryList.isNotEmpty()) return
        handleRepositoryCall(
            call = {
                categoryRepository.getCategories().map { it.toCategoryModel() } },
            onSuccess = { categoryList ->
                val firstId = categoryList.firstOrNull()?.id
                val mappings = categoryHeaderMapping(categoryList)

                copy(
                    categoryList = categoryList,
                    selectedCategoryId = firstId,
                    headerPositions = mappings
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

    private fun categoryHeaderMapping(categories: List<CategoryModel>): List<Pair<Int, String>> {
        val list = mutableListOf<Pair<Int, String>>()
        var index = 0
        categories.forEach { category ->
            list += index to category.id
            category.categories.forEach { subCategory ->
                index += 1 + subCategory.subCategories.size
            }
        }
        return list
    }
}