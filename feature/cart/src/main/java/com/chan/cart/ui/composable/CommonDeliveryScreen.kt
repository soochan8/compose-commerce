package com.chan.cart.ui.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chan.android.ui.theme.LightGray
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White
import com.chan.android.ui.theme.appTypography
import com.chan.cart.CartContract

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.commonDeliveryScreen(
    state: CartContract.State,
    onEvent: (CartContract.Event) -> Unit
) {
    item { SelectionToolbar(state = state, onEvent = onEvent) }
    stickyHeader { ShippingStickyHeader(state = state, onEvent = onEvent) }
    items(state.cartInProducts) { product ->
        CartProduct(product = product, onEvent = onEvent)
        HorizontalDivider(color = LightGray, thickness = 1.dp)
    }
}

@Composable
private fun SelectionToolbar(state: CartContract.State, onEvent: (CartContract.Event) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(Spacing.spacing4),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CartCheckBox(
            isSelected = state.allSelected,
            onClick = {
                onEvent(CartContract.Event.OnAllSelected)
            }
        )

        Spacer(modifier = Modifier.width(Spacing.spacing2))

        Text("전체", style = MaterialTheme.appTypography.cartTotalCount)
    }
}

@Composable
private fun ShippingStickyHeader(
    state: CartContract.State,
    onEvent: (CartContract.Event) -> Unit
) {
    Column(modifier = Modifier.background(Color.White)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF95C11F).copy(alpha = 0.2f))
                .padding(Spacing.spacing4),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CartCheckBox(
                isSelected = state.allSelected,
                onClick = {
                    onEvent(CartContract.Event.OnAllSelected)
                }
            )

            Spacer(modifier = Modifier.width(Spacing.spacing2))
            Text(
                text = state.tobBars[state.selectedTabIndex].title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        //일반 배송에만 보이도록
        if (state.selectedTabIndex == 0) {
            FreeShippingProgressBar(state.totalPrice)
        }
    }
}