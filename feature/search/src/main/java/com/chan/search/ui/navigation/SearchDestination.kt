package com.chan.search.ui.navigation

import androidx.navigation.NamedNavArgument
import com.chan.navigation.NavDestination
import com.chan.navigation.Routes

object SearchDestination : NavDestination {
    override val route = Routes.SEARCH.route
    override val arguments: List<NamedNavArgument> = emptyList()
}