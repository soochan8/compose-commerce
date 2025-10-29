package com.chan.android.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.chan.android.ui.theme.Black

@Composable
fun CommonTopBar(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedTextColor: Color = Color.Black,
    unselectedTextColor: Color = Color.Gray,
    indicatorColor: Color = Color.Black
) {
    var textWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = if (isSelected) selectedTextColor else unselectedTextColor,
            onTextLayout = { textLayoutResult ->
                textWidth = with(density) { textLayoutResult.size.width.toDp() }
            },
            modifier = Modifier.padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .width(textWidth)
                .height(2.dp)
                .background(if (isSelected) indicatorColor else Color.Transparent)
        )
    }
}


//@Composable
//fun CommonTopBar(
//    title: String,
//    isSelected: Boolean,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    var textWidth by remember { mutableStateOf(0.dp) }
//    val density = LocalDensity.current
//
//    Column(
//        modifier = modifier
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null,
//                onClick = onClick
//            ),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = title,
//            color = if (isSelected) Color.Black else Color.Gray,
//            onTextLayout = { textLayoutResult ->
//                textWidth = with(density) { textLayoutResult.size.width.toDp() }
//            },
//            modifier = Modifier.padding(top = 10.dp)
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Box(
//            modifier = Modifier
//                .width(textWidth)
//                .height(2.dp)
//                .background(if (isSelected) Color.Black else Color.Transparent)
//        )
//    }
//}