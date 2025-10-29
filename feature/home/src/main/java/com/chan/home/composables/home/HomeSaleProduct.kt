package com.chan.home.composables.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import coil3.compose.AsyncImage
import com.chan.android.ui.util.horizontalNestedScrollConnection
import com.chan.home.R
import com.chan.home.model.HomeSaleProductModel


private const val GRID_CELL_ROW = 2

@Composable
fun HomeSaleProduct(
    saleProducts: List<HomeSaleProductModel>
) {

    val nestedScrollConnection = horizontalNestedScrollConnection()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.home_sale_product),
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(4.dp))

        LazyHorizontalGrid(
            rows = GridCells.Fixed(GRID_CELL_ROW),
            modifier = Modifier
                .fillMaxWidth()
                .height(550.dp)
                .nestedScroll(nestedScrollConnection),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = saleProducts,
                key = { product -> product.productId },
                contentType = { "SaleProduct" }) { product ->
                SaleProductCard(product)
            }
        }
    }
}

@Composable
fun SaleProductCard(product: HomeSaleProductModel) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(160.dp)
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.productName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.productName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    lineHeight = 14.sp,
                    fontSize = 12.sp,
                    color = Color.Black,
                ),
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis

            )

            //가격
            Text(
                text = product.originalPrice,
                style = MaterialTheme.typography.bodySmall,
                textDecoration = TextDecoration.LineThrough,
                color = Color.Gray
            )
            Row {
                Text(
                    text = product.discountPercent,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = product.discountPrice,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            //태그
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                product.tags.let {
                    Text(
                        text = if (it.isNotEmpty()) {
                            it.joinToString(", ")
                        } else "",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF00AA00)
                    )
                }
            }
        }
    }
}

