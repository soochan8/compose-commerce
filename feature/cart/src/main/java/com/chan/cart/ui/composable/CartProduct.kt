package com.chan.cart.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.chan.android.ui.theme.Radius
import com.chan.android.ui.theme.Red
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.appTypography
import com.chan.cart.CartContract
import com.chan.cart.model.CartInProductsModel

@Composable
fun CartProduct(
    product: CartInProductsModel,
    onEvent: (CartContract.Event) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = Spacing.spacing4, vertical = Spacing.spacing6)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(120.dp)
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.productName,
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(Radius.radius1)),
                    contentScale = ContentScale.Crop,
                )

                CartCheckBox(
                    isSelected = product.isSelected,
                    onClick = {
                        onEvent(
                            CartContract.Event.UpdateProductSelected(
                                productId = product.productId,
                                isSelected = !product.isSelected
                            )
                        )
                    },
                    modifier = Modifier.align(Alignment.TopStart)
                )
            }
            Spacer(modifier = Modifier.width(Spacing.spacing4))
            Column(
                modifier = Modifier
                    .weight(2f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(product.productName, style = MaterialTheme.appTypography.productTitle)
                Spacer(modifier = Modifier.height(Spacing.spacing1))
                Text(
                    text = "오늘드림",
                    style = MaterialTheme.appTypography.tagLabel.copy(color = Red),
                    modifier = Modifier
                        .border(1.dp, Red, RoundedCornerShape(Radius.radius1))
                        .padding(Spacing.spacing1)
                )
            }
            IconButton(
                onClick = { onEvent(CartContract.Event.DeleteProduct(product.productId)) },
                modifier = Modifier
                    .align(Alignment.Top)
                    .size(Spacing.spacing5)
            ) {
                Icon(Icons.Default.Close, contentDescription = "삭제")
            }
        }

        Spacer(modifier = Modifier.height(Spacing.spacing4))

        Row(verticalAlignment = Alignment.CenterVertically) {
            QuantityStepper(
                productId = product.productId,
                quantity = product.quantity,
                onEvent = onEvent
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = product.originalPrice,
                    style = MaterialTheme.appTypography.originPrice.copy(fontSize = 12.sp),
                    modifier = Modifier.padding(end = Spacing.spacing2)
                )
                Text(
                    text = product.discountPriceLabel,
                    style = MaterialTheme.appTypography.discountPrice.copy(fontSize = 16.sp)
                )
            }
        }
    }
}

@Composable
fun QuantityStepper(
    productId: String,
    quantity: Int,
    onEvent: (CartContract.Event) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(1.dp, Color.LightGray, RoundedCornerShape(Radius.radius1))
            .width(120.dp)
            .height(35.dp)
    ) {
        IconButton(
            onClick = {
                onEvent(
                    CartContract.Event.UpdateProductQuantity(
                        productId = productId,
                        isAdd = false
                    )
                )
            },
            enabled = quantity > 1,
            modifier = Modifier
                .size(20.dp)
                .weight(1f)
        ) {
            Icon(Icons.Default.Close, contentDescription = "수량 감소")
        }
        Text(
            text = quantity.toString(),
            modifier = Modifier
                .padding(horizontal = Spacing.spacing4)
                .weight(1f)
        )
        IconButton(
            onClick = {
                onEvent(
                    CartContract.Event.UpdateProductQuantity(
                        productId = productId,
                        isAdd = true
                    )
                )
            },
            modifier = Modifier
                .size(20.dp)
                .weight(1f)
        ) {
            Icon(Icons.Default.Add, contentDescription = "수량 증가")
        }
    }
}