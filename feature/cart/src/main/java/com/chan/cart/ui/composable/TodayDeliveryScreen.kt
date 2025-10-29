package com.chan.cart.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.chan.android.ui.theme.Spacing
import androidx.compose.foundation.layout.Arrangement

@Composable
fun TodayDeliveryScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .background(Color.White)
            .padding(Spacing.spacing4),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "오늘 드림 화면 준비 중 입니다."
        )
    }
}