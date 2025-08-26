package com.chan.search.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White
import com.chan.search.R
import com.chan.search.ui.contract.SearchContract
import com.chan.search.ui.model.filter.DeliveryOption
import com.chan.search.ui.model.filter.FilterCategoriesModel
import com.chan.search.ui.model.filter.FilterCategoryListModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFilterScreen(
    state: SearchContract.State,
    onEvent: (SearchContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier,
        containerColor = White,
        topBar = {
            FilterHeader(
                onClose = { onEvent(SearchContract.Event.Filter.OnFilterClick) },
                onFilterClear = { onEvent(SearchContract.Event.Filter.OnClear) }
            )
        },
        bottomBar = {
            FilterBottomButton(
                itemCount = state.filter.filteredProductCount,
                onApplyFilters = { onEvent(SearchContract.Event.Filter.OnFilterClick) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            // "오늘드림", "픽업" 체크박스 섹션
            item {
                FilterToggleSection(
                    selectedOption = state.filter.selectedDeliveryOption,
                    onOptionClick = { onEvent(SearchContract.Event.Filter.OnDeliveryOptionChanged(it)) }
                )
                HorizontalDivider(
                    color = Color.LightGray.copy(alpha = 0.2f),
                    modifier = Modifier
                        .padding(vertical = Spacing.spacing2),
                    thickness = Spacing.spacing2
                )
            }

            item {
                //카테고리
                ExpandableFilterSection(
                    title = stringResource(R.string.category_label),
                    isExpanded = state.filter.isCategorySectionExpanded,
                    onClick = { onEvent(SearchContract.Event.Filter.OnCategoryClick) }
                )
                HorizontalDivider(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    thickness = 1.dp
                )
                //서브 카테고리
                if (state.filter.isCategorySectionExpanded) {
                    SubFilterCategory(
                        categoryFilters = state.filter.categoryFilters,
                        expandedCategoryName = state.filter.expandedCategoryName,
                        selectedSubCategories = state.filter.selectedSubCategories,
                        onCategoryHeaderClick = {
                            onEvent(
                                SearchContract.Event.Filter.OnCategoryHeaderClick(
                                    it
                                )
                            )
                        },
                        onSubCategoryClick = { onEvent(SearchContract.Event.Filter.OnSubCategoryClick(it)) }
                    )
                }
            }

            item {
                ExpandableFilterSection(title = stringResource(R.string.price_label))
                HorizontalDivider(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    thickness = 1.dp
                )
            }

            item {
                ExpandableFilterSection(
                    title = stringResource(R.string.product_view_mode_label),
                    details = "2단"
                )
                HorizontalDivider(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
private fun SubFilterCategory(
    categoryFilters: List<FilterCategoryListModel>,
    expandedCategoryName: String?,
    selectedSubCategories: Set<String>,
    onCategoryHeaderClick: (categoryName: String) -> Unit,
    onSubCategoryClick: (subCategoryName: String) -> Unit,
) {
    Column {
        categoryFilters.forEach { category ->
            ExpandableFilterSection(
                title = category.parent.name,
                isExpanded = expandedCategoryName == category.parent.name,
                onClick = { onCategoryHeaderClick(category.parent.name) }
            )
            if (expandedCategoryName == category.parent.name) {
                Column(modifier = Modifier.padding(start = Spacing.spacing4)) {
                    category.children.forEach { subCategory ->
                        FilterCheckboxItem(
                            label = subCategory.name,
                            checked = selectedSubCategories.contains(subCategory.name),
                            onOptionClick = { onSubCategoryClick(subCategory.name) }
                        )
                    }
                }
            }
            HorizontalDivider(
                color = Color.LightGray.copy(alpha = 0.5f),
                thickness = 1.dp
            )
        }
    }
}

// 상단 헤더: "필터", 초기화, 닫기 버튼
@Composable
fun FilterHeader(
    onClose: () -> Unit,
    onFilterClear: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "필터",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "필터 초기화",
            modifier = Modifier
                .size(28.dp)
                .clickable(onClick = onFilterClear)
        )
        Spacer(Modifier.width(16.dp))
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "필터 닫기",
            modifier = Modifier
                .size(28.dp)
                .clickable { onClose() }
        )
    }
}

// "오늘드림", "픽업" 체크박스 영역
@Composable
fun FilterToggleSection(selectedOption: DeliveryOption?, onOptionClick: (DeliveryOption) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = Spacing.spacing4)) {
        FilterCheckboxItem(label = stringResource(R.string.today_delivery_label),
            checked = selectedOption == DeliveryOption.TODAY_DELIVERY,
            onOptionClick = { onOptionClick(DeliveryOption.TODAY_DELIVERY) }
        )
        FilterCheckboxItem(label = stringResource(R.string.pick_up_label),
            checked = selectedOption == DeliveryOption.PICKUP, onOptionClick = {
                onOptionClick(DeliveryOption.PICKUP)
            })
    }
}

@Composable
fun FilterCheckboxItem(label: String, checked: Boolean, onOptionClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = Spacing.spacing3)
            .clickable(onClick = onOptionClick)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                uncheckedColor = Color.Gray,
                checkedColor = Color.Black
            )
        )
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun ExpandableFilterSection(
    title: String,
    details: String? = null,
    isExpanded: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(Spacing.spacing4),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.weight(1f))
        details?.let { subDetailText ->
            Text(
                text = subDetailText,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier.padding(end = Spacing.spacing2)
            )
        }
        Icon(
            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = "더보기",
            tint = Color.Gray
        )
    }
}

// 하단 "N개 상품 보기" 버튼
@Composable
fun FilterBottomButton(
    itemCount: Int,
    onApplyFilters: () -> Unit
) {
    Button(
        onClick = onApplyFilters,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.spacing4),
        shape = RoundedCornerShape(Spacing.spacing2),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        )
    ) {
        Text(
            text = "%,d개 상품 보기".format(itemCount),
            modifier = Modifier.padding(vertical = Spacing.spacing2),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}




