package com.chan.search.ui.actions

import androidx.compose.runtime.Stable
import com.chan.search.ui.contract.SearchContract
import com.chan.search.ui.model.SearchResultFilterChipModel
import com.chan.search.ui.model.filter.DeliveryOption

@Stable
class FilterActions(private val onEvent: (SearchContract.Event) -> Unit) {
    val onCategoryClick: () -> Unit = { onEvent(SearchContract.Event.Filter.OnCategoryClick) }
    val onFilterClick: () -> Unit = { onEvent(SearchContract.Event.Filter.OnFilterClick) }
    val onClear: () -> Unit = { onEvent(SearchContract.Event.Filter.OnClear) }

    fun onDeliveryOptionChanged(option: DeliveryOption) =
        onEvent(SearchContract.Event.Filter.OnDeliveryOptionChanged(option))

    fun onCategoryHeaderClick(categoryName: String) =
        onEvent(SearchContract.Event.Filter.OnCategoryHeaderClick(categoryName))

    fun onSubCategoryClick(subCategoryName: String) =
        onEvent(SearchContract.Event.Filter.OnSubCategoryClick(subCategoryName))

    fun onFilterChipClicked(chip: SearchResultFilterChipModel) =
        onEvent(SearchContract.Event.Filter.OnFilterChipClicked(chip))
}