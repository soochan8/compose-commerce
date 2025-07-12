package com.chan.android

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chan.android.model.Price
import com.chan.android.model.ProductModel
import com.chan.android.model.Review

@Composable
fun ProductCard(
    product: ProductModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.productName,
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = product.productName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                maxLines = 3
            )
            Spacer(modifier = Modifier.height(10.dp))

            Price(product.price)
            Spacer(modifier = Modifier.height(10.dp))

            product.review?.let { review ->
                Review(review)
            }
        }
    }
}

@Composable
fun Price(price: Price) {
    Row {
        Text(
            text = "${price.discountPercent}%",
            fontSize = 18.sp,
            color = Color.Red,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${price.discountedPrice}원",
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
    Text(
        text = "${price.originPrice}원",
        fontSize = 14.sp,
        color = Color.LightGray,
        fontWeight = FontWeight.Medium,
        style = TextStyle(textDecoration = TextDecoration.LineThrough)
    )
}

@Composable
fun Review(reviews: Review) {
    Row {
        Image(
            imageVector = Icons.Default.Star,
            contentDescription = "star"
        )
        Text(
            text = reviews.reviewLabel,
            fontSize = 14.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Medium
        )
    }
}