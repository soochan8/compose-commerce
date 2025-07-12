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
        handleRepositoryCall(
            call = { categoryDetailRepository.getCategoryNames().map { it.toPresentation() } },
            onSuccess = { detailNames -> copy(categoryNames = detailNames) }
        )
    }

    fun getCategoryDetailList() {
        handleRepositoryCall(
            call = {
                categoryDetailRepository.getCategoryDetails().map { it.toPresentationModel() }
            },
            onSuccess = { detailLists -> copy(categoryDetailList = detailLists) }
        )
    }

    private fun <T> handleRepositoryCall(
        call: suspend () -> T,
        onSuccess: CategoryDetailContract.State.(T) -> CategoryDetailContract.State
    ) {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }
            try {
                val result = call()
                setState { onSuccess(result) }
            } catch (e: Exception) {
                setState { copy(isLoading = false, isError = true) }
                setEffect { CategoryDetailContract.Effect.ShowError(e.message.toString()) }
            }
        }
    }
}