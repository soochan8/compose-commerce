package com.chan.search.ui.composables.result

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chan.android.ProductCard
import com.chan.android.model.ProductModel
import com.chan.android.ui.theme.LightGray
import com.chan.android.ui.theme.Radius
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White
import com.chan.android.ui.theme.appTypography
import com.chan.android.ui.theme.dividerColor
import com.chan.search.ui.model.FilterChipType
import com.chan.search.ui.model.SearchResultFilterChipModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchResultScreenContent(
    products: List<ProductModel>,
    filters: List<SearchResultFilterChipModel>,
    onToggleFilter: (SearchResultFilterChipModel) -> Unit,
    onFilterClick: () -> Unit,
    onProductClick: (String) -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("상품", "후기", "콘텐츠")
    val lazyListState = rememberLazyListState()

    LaunchedEffect(products) {
        if (products.isNotEmpty()) {
            lazyListState.scrollToItem(0)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState
    ) {
        item {
            SearchResultTabRow(
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it }
            )
        }

        stickyHeader {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = White
            ) {
                Column {
                    FilterChipsRow(
                        filters = filters,
                        onToggleFilter = onToggleFilter,
                        onFilterClick = onFilterClick,
                    )
                    HorizontalDivider(color = dividerColor, thickness = 1.dp)
                }
            }
        }

        when (selectedTabIndex) {
            0 -> { // 상품
                item {
                    SearchResultListHeader(products)
                }
                productGrid(
                    products = products,
                    onProductClick = onProductClick
                )
            }

            1 -> { // 후기
                item { Text("후기 탭 내용") }
            }

            2 -> { // 콘텐츠
                item { Text("콘텐츠 탭 내용") }
            }
        }
    }
}

private fun LazyListScope.productGrid(
    products: List<ProductModel>,
    onProductClick: (String) -> Unit
) {
    val chunkedProducts = products.chunked(2)
    items(
        items = chunkedProducts,
        key = { row -> row.joinToString { it.productId } }
    ) { productRow ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2)
        ) {
            productRow.forEach { product ->
                Box(modifier = Modifier.weight(1f)) {
                    ProductCard(
                        product = product,
                        onClick = { onProductClick(product.productId) },
                        onLikeClick = {},
                        onCartClick = {}
                    )
                }
            }
            if (productRow.size == 1) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun CustomTab(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var textWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = Spacing.spacing4)
    ) {
        Text(
            text = title,
            color = if (isSelected) Color.Black else Color.Gray,
            style = MaterialTheme.appTypography.tab.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            ),
            onTextLayout = { textLayoutResult ->
                textWidth = with(density) { textLayoutResult.size.width.toDp() }
            },
            modifier = Modifier
                .padding(vertical = Spacing.spacing4)
                .align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .width(textWidth)
                .height(2.dp)
                .background(if (isSelected) Color.Black else Color.Transparent)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun SearchResultTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Column(modifier = Modifier.background(Color.White)) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, title ->
                CustomTab(
                    title = title,
                    isSelected = index == selectedTabIndex,
                    onClick = { onTabSelected(index) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        HorizontalDivider(color = dividerColor, thickness = 1.dp)
    }
}

@Composable
private fun FilterChipsRow(
    filters: List<SearchResultFilterChipModel>,
    onToggleFilter: (SearchResultFilterChipModel) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = Spacing.spacing4, vertical = Spacing.spacing2),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            filters.forEach { filter ->
                FilterChip(
                    filter = filter,
                    onToggleFilter = onToggleFilter,
                    onFilterClick = onFilterClick,
                )
            }
        }
    }
}

@Composable
private fun FilterChip(
    filter: SearchResultFilterChipModel,
    onToggleFilter: (SearchResultFilterChipModel) -> Unit,
    onFilterClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val containerColor = if (filter.isSelected) Color.Black else Color.White
    val textColor = if (filter.isSelected) Color.White else Color.DarkGray

    Box(
        modifier = Modifier
            .background(color = containerColor, shape = RoundedCornerShape(Radius.radius6))
            .clip(RoundedCornerShape(Radius.radius6))
            .border(width = 1.dp, color = LightGray, shape = RoundedCornerShape(Radius.radius6))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    when (filter.chipType) {
                        FilterChipType.TOGGLE -> onToggleFilter(filter)
                        FilterChipType.DROP_DOWN -> onFilterClick()
                    }
                }
            )
            .padding(horizontal = Spacing.spacing3, vertical = Spacing.spacing2)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            when (filter.chipType) {
                FilterChipType.TOGGLE -> {
                    AsyncImage(
                        model = filter.image,
                        contentDescription = filter.text,
                        modifier = Modifier
                            .size(Spacing.spacing5)
                            .padding(end = Spacing.spacing1)
                    )
                    Text(text = filter.text, color = textColor)
                }

                FilterChipType.DROP_DOWN -> {
                    Text(
                        text = filter.text,
                        color = textColor,
                        modifier = Modifier.padding(end = Spacing.spacing1)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown",
                        tint = textColor,
                        modifier = Modifier.size(Spacing.spacing4)
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchResultListHeader(products: List<ProductModel>) {
    Text(text = "총 ${products.size}개", modifier = Modifier.padding(Spacing.spacing4))
}