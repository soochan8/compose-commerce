package com.chan.product.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chan.android.ui.theme.Black
import com.chan.android.ui.theme.Gray
import com.chan.android.ui.theme.Radius
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White
import com.chan.android.ui.theme.appTypography
import com.chan.product.R
import com.chan.product.ui.model.ProductDetailModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductPurchaseBottomBar(productInfo: ProductDetailModel.ProductInfoModel) {
    var isExpanded by remember { mutableStateOf(false) }
    var quantity by remember { mutableStateOf(1) }
    var isTodayDeliveryChecked by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(
            topStart = Radius.radius4,
            topEnd = Radius.radius4
        ),
        color = White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = "expand",
                    modifier = Modifier.size(32.dp)
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                PurchaseOptions(
                    quantity = quantity,
                    onQuantityChange = { newQuantity -> quantity = newQuantity },
                    priceInfo = productInfo.price
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.spacing4),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isTodayDeliveryChecked,
                        onCheckedChange = { isTodayDeliveryChecked = !isTodayDeliveryChecked },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Black,
                            uncheckedColor = Gray,
                            checkmarkColor = White
                        ),
                        modifier = Modifier.size(Spacing.spacing5)
                    )
                    Spacer(modifier = Modifier.width(Spacing.spacing2))
                    Text(
                        text = stringResource(R.string.bottom_bar_today_delivery_label),
                        style = MaterialTheme.appTypography.bottomBarTodayDelivery
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.spacing4)
                    .padding(bottom = Spacing.spacing1),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2)
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(Radius.radius1),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color.Gray)
                ) {
                    Text(
                        text = stringResource(R.string.cart),
                        style = MaterialTheme.appTypography.bottomBarButton.copy(
                            color = Black
                        )
                    )
                }

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(Radius.radius1),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )
                ) {
                    Text(
                        text = stringResource(R.string.buy_now),
                        style = MaterialTheme.appTypography.bottomBarButton.copy(
                            color = White
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun PurchaseOptions(
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    priceInfo: ProductDetailModel.ProductInfoModel.PriceModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.spacing4, horizontal = Spacing.spacing5),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            QuantitySelector(quantity = quantity, onQuantityChange = onQuantityChange)
            Text(
                text = priceInfo.discountPriceLabel,
                style = MaterialTheme.appTypography.purchasePriceLabel
            )
        }

        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.spacing5, horizontal = Spacing.spacing5),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.purchase_quantity_label, quantity),
                style = MaterialTheme.appTypography.purchaseQuantityLabel
            )
            Text(
                text = stringResource(
                    R.string.total_price_label,
                    formatPrice(quantity * priceInfo.discountPrice)
                ),
                style = MaterialTheme.appTypography.purchaseTotalPrice
            )
        }
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
    }
}

@Composable
private fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(105.dp)
            .height(36.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = MaterialTheme.shapes.small
            ),
    ) {
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "수량 감소",
            modifier = Modifier
                .size(12.dp)
                .weight(1f)
                .clickable(
                    onClick = { if (quantity > 1) onQuantityChange(quantity - 1) },
                    interactionSource = null,
                    indication = null
                )
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = Color.LightGray.copy(alpha = 0.5f)
        )
        Text(
            text = quantity.toString(),
            modifier = Modifier
                .padding(horizontal = Spacing.spacing3)
                .weight(1f),
            style = MaterialTheme.appTypography.purchaseQuantity
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = Color.LightGray.copy(alpha = 0.5f)
        )
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "수량 증가",
            modifier = Modifier
                .size(Spacing.spacing3)
                .weight(1f)
                .clickable(
                    onClick = { onQuantityChange(quantity + 1) },
                    interactionSource = null,
                    indication = null
                )
        )
    }
}

private fun formatPrice(price: Int): String {
    return "${NumberFormat.getNumberInstance(Locale.KOREA).format(price)}"
}