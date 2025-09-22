package com.chan.android.ui.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.chan.android.ui.theme.White
import com.chan.android.ui.theme.appTypography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    navigationIcon: @Composable (() -> Unit)? = null,
    titleContent: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    titleStyle: TextStyle = MaterialTheme.appTypography.title
) {
    TopAppBar(
        navigationIcon = { navigationIcon?.invoke() },
        title = { ProvideTextStyle(titleStyle) { titleContent() } },
        actions = { actions() },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White,
            scrolledContainerColor = White
        )
    )
}
