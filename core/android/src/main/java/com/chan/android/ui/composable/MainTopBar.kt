package com.chan.android.ui.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.chan.android.ui.theme.Black
import com.chan.android.ui.theme.White
import com.chan.android.ui.theme.appTypography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    navigationIcon: @Composable (() -> Unit)? = null,
    titleContent: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    titleStyle: TextStyle = MaterialTheme.appTypography.title,
    centerAligned: Boolean = false,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = White,
        scrolledContainerColor = White
    )
) {
    val currentColors = colors
    if (centerAligned) {
        CenterAlignedTopAppBar(
            navigationIcon = { navigationIcon?.invoke() },
            title = { ProvideTextStyle(titleStyle) { titleContent() } },
            actions = { actions() },
            scrollBehavior = scrollBehavior,
            colors = currentColors
        )
    } else {
        TopAppBar(
            navigationIcon = { navigationIcon?.invoke() },
            title = { ProvideTextStyle(titleStyle) { titleContent() } },
            actions = { actions() },
            scrollBehavior = scrollBehavior,
            colors = currentColors
        )
    }
}
