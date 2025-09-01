package com.chan.cart.ui

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.chan.cart.CartContract

@Composable
fun CartScreen(
    state: CartContract.State,
    onEvent: (CartContract.Event) -> Unit
) {
    Text(text = "장바구니 화면")
}