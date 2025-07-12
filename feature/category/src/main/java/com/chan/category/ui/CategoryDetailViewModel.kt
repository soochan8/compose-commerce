package com.chan.category.ui

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.category.domian.CategoryDetailRepository
import com.chan.category.ui.mapper.toPresentation
import com.chan.category.ui.mapper.toPresentationModel
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
            CategoryDetailContract.Event.CategoryDetailListLoad -> getCategoryDetailList()
            CategoryDetailContract.Event.CategoryDetailNamesLoad -> getCategoryDetailNames()
        }
    }

    fun getCategoryDetailNames() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            try {
                val categoryNames =
                    categoryDetailRepository.getCategoryNames().map { it.toPresentation() }
                setState { copy(categoryNames = categoryNames) }
            } catch (e: Exception) {
                setState { copy(isLoading = false, isError = true) }
                setEffect { CategoryDetailContract.Effect.ShowError(e.message.toString()) }
            }
        }
    }

    fun getCategoryDetailList() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            try {
                val categoryDetails =
                    categoryDetailRepository.getCategoryDetails().map { it.toPresentationModel() }
                setState { copy(categoryDetailList = categoryDetails) }
            } catch (e: Exception) {
                setState { copy(isLoading = false, isError = true) }
                setEffect { CategoryDetailContract.Effect.ShowError(e.message.toString()) }
            }
        }
    }
}