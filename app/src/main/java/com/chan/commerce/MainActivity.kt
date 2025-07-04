package com.chan.commerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chan.home.navigation.HomeDestination
import com.chan.navigation.BottomNavigationBar
import com.chan.navigation.NavDestinationProvider
import com.chan.navigation.NavGraphProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navGraphProviders: Set<@JvmSuppressWildcards NavGraphProvider>
    @Inject
    lateinit var navDestinationProviders: Set<@JvmSuppressWildcards NavDestinationProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentDestination = navController.currentBackStackEntryAsState().value
            val currentRoute = currentDestination?.destination?.route

            val allNavDestinations = remember(navDestinationProviders) {
                navDestinationProviders.flatMap {
                    it.getDestination()
                }
            }

            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            navController.navigate(route) {
                                launchSingleTop = true
                                if (route == HomeDestination.route) popUpTo(0)
                            }
                        },
                        navDestinations = allNavDestinations
                    )
                }
            ) { padding ->
                AppNavHost(
                    navController = navController,
                    navGraphProviders = navGraphProviders,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    navGraphProviders: Set<@JvmSuppressWildcards NavGraphProvider>,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        navGraphProviders.forEach { provider ->
            provider.addGraph(this, navController)
        }
    }
}