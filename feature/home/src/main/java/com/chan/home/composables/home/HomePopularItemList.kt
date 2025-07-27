package com.chan.home.composables.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chan.android.ui.util.horizontalNestedScrollConnection
import com.chan.home.R
import com.chan.home.model.HomePopularItemModel

@Composable
fun HomePopularItemList(
    popularItem: List<HomePopularItemModel>,
    onItemClick: (productId: String) -> Unit
) {
    val nestedScrollConnection = horizontalNestedScrollConnection()

    Text(
        text = stringResource(R.string.home_popular_product),
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, bottom = 8.dp),
        style = MaterialTheme.typography.bodyLarge.copy(
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        ),
    )

    LazyRow(
        contentPadding = PaddingValues(start = 8.dp),
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {
        items(
            items = popularItem,
            key = { it.productId }
        ) { item ->
            HomePopularItem(
                popularItem = item,
                onClick = { onItemClick(item.productId) }
            )
        }
    }

}

@Composable
fun HomePopularItem(
    popularItem: HomePopularItemModel,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .padding(end = 12.dp)
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = popularItem.imageUrl,
            contentDescription = popularItem.productName,
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = popularItem.productName,
            style = MaterialTheme.typography.bodyMedium.copy(
                lineHeight = 14.sp,
                fontSize = 12.sp,
                color = Color.Black,
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis

        )
        Spacer(modifier = Modifier.height(2.dp))

        Price(popularItem)

        if (popularItem.tags.isNotEmpty()) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                popularItem.tags.forEach { tag ->
                    TagChip(tagLabel = tag)
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        Review(popularItem)
    }
}

@Composable
private fun Price(popularItem: HomePopularItemModel) {
    Text(
        text = popularItem.discountPrice,
        style = MaterialTheme.typography.labelSmall.copy(
            fontSize = 10.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Normal,
            textDecoration = TextDecoration.LineThrough
        )
    )

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = popularItem.discountPercent,
            style = MaterialTheme.typography.labelMedium.copy(
                color = Color.Red,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = popularItem.originalPrice,
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        )
    }

    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
private fun TagChip(tagLabel: String) {
    val textColor = when (tagLabel) {
        stringResource(R.string.today_delivery_label) -> Color(0xFFC56692)
        else -> Color(0xFF666666)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFFF1F1F1))
            .padding(horizontal = 6.dp)
    ) {
        Text(
            text = tagLabel,
            style = MaterialTheme.typography.labelSmall.copy(
                color = textColor,
                fontSize = 9.sp,
                fontWeight = FontWeight.Normal
            ),
            maxLines = 1

        )
    }
}

@Composable
private fun Review(popularItem: HomePopularItemModel) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "평점",
            tint = Color(0xFF98A1A8),
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = popularItem.reviewRating,
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color.Gray,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
            ),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = popularItem.reviewCount,
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color.Gray,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
            ),
        )
    }
}