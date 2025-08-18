package com.chan.search.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import com.chan.search.ui.model.filter.DeliveryOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFilterScreen(
    selectedDeliveryOption: DeliveryOption?,
    onClose: () -> Unit,
    onDeliveryOptionClick: (DeliveryOption) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier,
        containerColor = White,
        topBar = { FilterHeader(onClose = onClose) },
        bottomBar = { FilterBottomButton(itemCount = 2878) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            // "오늘드림", "픽업" 체크박스 섹션
            item {
                FilterToggleSection(
                    selectedOption = selectedDeliveryOption,
                    onOptionClick = onDeliveryOptionClick
                )
                HorizontalDivider(
                    color = Color.LightGray.copy(alpha = 0.2f),
                    modifier = Modifier
                        .padding(vertical = Spacing.spacing2),
                    thickness = Spacing.spacing2
                )
            }

            // 확장 가능한 필터 카테고리 목록
            val filterCategories = listOf(
                "카테고리", "가격"
            )
            items(filterCategories) { categoryTitle ->
                ExpandableFilterSection(title = categoryTitle)
                HorizontalDivider(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    thickness = 1.dp
                )
            }

            item {
                ExpandableFilterSection(title = "상품 보기 방식", details = "2단")
                HorizontalDivider(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    thickness = 1.dp
                )
            }
        }
    }
}

// 상단 헤더: "필터", 초기화, 닫기 버튼
@Composable
fun FilterHeader(onClose: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.spacing4, vertical = Spacing.spacing3),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.filter_label),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "필터 초기화",
            modifier = Modifier.size(28.dp)
        )
        Spacer(Modifier.width(Spacing.spacing4))
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
fun ExpandableFilterSection(title: String, details: String? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.spacing4),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.weight(1f))
        if (details != null) {
            Text(
                text = details,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier.padding(end = Spacing.spacing2)
            )
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "더보기",
            tint = Color.Gray
        )
    }
}

// 하단 "N개 상품 보기" 버튼
@Composable
fun FilterBottomButton(itemCount: Int) {
    Button(
        // onClick을 비워두어 클릭 비활성화
        onClick = { },
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