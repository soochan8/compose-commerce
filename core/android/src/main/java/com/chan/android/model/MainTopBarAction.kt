package com.chan.android.model

import androidx.compose.ui.graphics.vector.ImageVector

data class MainTopBarAction(
    val icon: ImageVector,
    val onClick: () -> Unit
)
