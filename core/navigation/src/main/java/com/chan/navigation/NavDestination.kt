package com.chan.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestination(
    val route: String,
    @StringRes val title: Int,
    val icon: ImageVector
) {
    object Category : NavDestination(CATEGORY, R.string.category_navigation, Icons.Default.Menu)
    object Home : NavDestination(HOME, R.string.home_navigation, Icons.Default.Home)
    object History : NavDestination(HISTORY, R.string.history_navigation, Icons.Default.Refresh)
    object MyPage : NavDestination(MY_PAGE, R.string.my_page_navigation, Icons.Default.Person)

    companion object {
        val navDestinations = listOf(Category, Home, History, MyPage)

        const val CATEGORY = "category"
        const val HOME = "home"
        const val HISTORY = "history"
        const val MY_PAGE = "mypage"
    }
}
