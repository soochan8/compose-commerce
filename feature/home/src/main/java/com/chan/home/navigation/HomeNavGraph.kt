package com.chan.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.chan.home.composables.HomeScreen
import com.chan.navigation.NavGraphProvider
import javax.inject.Inject

class HomeNavGraph @Inject constructor() : NavGraphProvider {
    override fun addGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(HomeDestination.route) {
            HomeScreen()
        }
    }

}