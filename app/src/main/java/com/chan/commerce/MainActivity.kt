package com.chan.commerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chan.category.ui.composables.CategoryScreen
import com.chan.home.composables.HomeScreen
import com.chan.home.history.HistoryScreen
import com.chan.home.mypage.MyPageScreen
import com.chan.navigation.BottomNavigationBar
import com.chan.navigation.NavDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentDestination = navController.currentBackStackEntryAsState().value
            val currentRoute = currentDestination?.destination?.route

            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            navController.navigate(route) {
                                launchSingleTop = true
                                if (route == NavDestination.Home.route) popUpTo(0)
                            }
                        }
                    )
                }
            ) { padding ->
                NavHost(
                    navController = navController,
                    startDestination = NavDestination.Home.route,
                    modifier = Modifier.padding(padding)
                ) {
                    composable(NavDestination.Home.route) { HomeScreen() }
                    composable(NavDestination.Category.route) { CategoryScreen() }
                    composable(NavDestination.History.route) { HistoryScreen() }
                    composable(NavDestination.MyPage.route) { MyPageScreen() }
                }
            }
        }
    }
}