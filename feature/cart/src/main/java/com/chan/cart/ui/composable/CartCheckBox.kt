package com.chan.cart.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chan.android.ui.theme.Black
import com.chan.android.ui.theme.LightGray
import com.chan.android.ui.theme.Radius
import com.chan.android.ui.theme.White

@Composable
fun CartCheckBox(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(20.dp)
            .background(
                color = if (isSelected) Black else White,
                shape = RoundedCornerShape(Radius.radius1)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) Color.Transparent else LightGray,
                shape = RoundedCornerShape(Radius.radius1)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}