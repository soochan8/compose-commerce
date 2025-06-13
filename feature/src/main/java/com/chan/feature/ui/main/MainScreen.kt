package com.chan.feature.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chan.feature.ui.common.BottomNavigationBar
import com.chan.feature.ui.common.BottomScreen
import com.chan.feature.ui.home.composables.HomeScreen


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value
    val currentRoute = currentDestination?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onNavigate = { route -> navController.navigate(route) }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomScreen.Home.route) { HomeScreen() }
            composable(BottomScreen.Category.route) {  }
            composable(BottomScreen.History.route) {  }
            composable(BottomScreen.MyPage.route) {  }
        }
    }
}