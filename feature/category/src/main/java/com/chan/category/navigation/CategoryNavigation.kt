package com.chan.category.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.chan.category.ui.composables.CategoryScreen
import com.chan.navigation.NavDestination

fun NavGraphBuilder.categoryNavGraph(navController: NavHostController) {
    composable(route = NavDestination.Home.route) {
        CategoryScreen()
    }
}