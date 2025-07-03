package com.chan.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.chan.home.composables.HomeScreen
import com.chan.navigation.NavDestination

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    composable(route = NavDestination.Home.route) {
        HomeScreen()
    }
}