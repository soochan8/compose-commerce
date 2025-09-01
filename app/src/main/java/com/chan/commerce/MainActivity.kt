package com.chan.commerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chan.home.navigation.HomeDestination
import com.chan.login.navigation.LoginDestination
import com.chan.navigation.BottomNavigationBar
import com.chan.navigation.NavDestinationProvider
import com.chan.navigation.NavGraphProvider
import com.chan.navigation.createLoginRoute
import com.chan.product.navigation.ProductDetailDestination
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
        setContent {
            val navController = rememberNavController()
            val currentDestination = navController.currentBackStackEntryAsState().value
            val currentRoute = currentDestination?.destination?.route
            val isBottomBarVisible = remember { mutableStateOf(true) }
            var bottomBarHeight by remember { mutableStateOf(0.dp) }
            val density = LocalDensity.current

            val nestedScrollConnection = remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(
                        available: Offset,
                        source: NestedScrollSource
                    ): Offset {
                        if (available.y < -1) {
                            isBottomBarVisible.value = false
                        }

                        if (available.y > 1) {
                            isBottomBarVisible.value = true
                        }
                        return Offset.Zero
                    }
                }
            }

            val bottomBarOffset by animateDpAsState(
                targetValue = if (isBottomBarVisible.value) 0.dp else bottomBarHeight,
                animationSpec = tween(durationMillis = 300),
                label = "bottomBarOffset"
            )

            val allNavDestinations = remember(navDestinationProviders) {
                navDestinationProviders.flatMap {
                    it.getDestination()
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(nestedScrollConnection)
            ) {
                AppNavHost(
                    navController = navController,
                    navGraphProviders = navGraphProviders,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(WindowInsets.statusBars.asPaddingValues())
                )

                //화면에 따라 BottomNavigationBar visibility 결정
                val isShowBottomBar = when (currentRoute) {
                    ProductDetailDestination.route -> false
                    else -> true
                }

                if (isShowBottomBar) {
                    BottomNavigationBar(
                        currentRoute = currentRoute,
                        onNavigate = { destination ->
                            val target = if (destination == LoginDestination) {
                                createLoginRoute()
                            } else {
                                destination.route
                            }

                            navController.navigate(target) {
                                launchSingleTop = true
                                if (destination.route == HomeDestination.route) popUpTo(0)
                            }
                        },
                        navDestinations = allNavDestinations,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = bottomBarOffset)
                            .onGloballyPositioned {
                                if (bottomBarHeight == 0.dp) {
                                    bottomBarHeight = with(density) {
                                        it.size.height.toDp()
                                    }
                                }
                            }
                    )
                }
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