package com.chan.category.navigation.detail

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.chan.navigation.NavDestination
import com.chan.navigation.Routes

object CategoryDetailDestination : NavDestination {
    override val route = Routes.CATEGORY_DETAIL.route
    override val arguments = listOf(
        navArgument("categoryId") { type = NavType.StringType }
    )
}