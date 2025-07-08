package com.chan.home.composables

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chan.home.R
import com.chan.home.model.HomePopularItemModel

@Composable
fun HomePopularItemList(
    popularItem: List<HomePopularItemModel>
) {

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                return Offset(x = available.x, y = 0f)
            }

            override suspend fun onPostFling(
                consumed: Velocity,
                available: Velocity
            ): Velocity {
                return Velocity(x = available.x, y = 0f)
            }
        }
    }


    Text(
        text = stringResource(R.string.home_popular_product),
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, bottom = 8.dp),
        style = MaterialTheme.typography.bodyLarge,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp),
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {
        items(popularItem) { item ->
            HomePopularItem(popularItem = item)
        }
    }

}

@Composable
fun HomePopularItem(popularItem: HomePopularItemModel) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .padding(end = 12.dp)
    ) {
        AsyncImage(
            model = popularItem.imageUrl,
            contentDescription = popularItem.name,
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = popularItem.name,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(2.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = popularItem.discountPercent,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = popularItem.originPrice,
                style = MaterialTheme.typography.labelSmall.copy(
                    textDecoration = TextDecoration.LineThrough,
                    color = Color.Gray
                )
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = popularItem.discountedPrice,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(2.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "평점",
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = popularItem.rating,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }
    }
}