package com.chan.cart.ui.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.chan.android.ui.theme.Radius
import com.chan.android.ui.theme.Spacing
import com.chan.cart.R

const val FREE_SHIPPING_THRESHOLD = 50000

@Composable
fun FreeShippingProgressBar(
    totalPrice: Int
) {
    val freeShippingThreshold = 50000

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.spacing4, vertical = Spacing.spacing2)
    ) {
        Text(
            text = stringResource(R.string.free_shipping_condition, freeShippingThreshold),
            fontSize = 14.sp,
            color = Color.Gray
        )
    }

    val progress =
        (totalPrice.coerceAtMost(FREE_SHIPPING_THRESHOLD).toFloat() / FREE_SHIPPING_THRESHOLD)
    LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
            .fillMaxWidth()
            .height(Spacing.spacing1)
            .padding(horizontal = Spacing.spacing4)
            .clip(RoundedCornerShape(Radius.radius1)),
        color = Color(0xFF95C11F),
        trackColor = Color.LightGray.copy(alpha = 0.3f)
    )
}