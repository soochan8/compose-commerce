package com.chan.cart.naivgation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.chan.navigation.NavDestination
import com.chan.navigation.Routes

object CartDestination : NavDestination {
    override val route = Routes.CART.route
    override val arguments: List<NamedNavArgument> = emptyList()
}

object CartPopupDestination : NavDestination {
    override val route = Routes.CART_POPUP.route
    override val arguments = listOf(
        navArgument("productId") { type = NavType.StringType }
    )
}