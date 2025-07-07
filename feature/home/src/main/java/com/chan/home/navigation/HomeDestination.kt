package com.chan.home.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.navigation.NamedNavArgument
import com.chan.home.R
import com.chan.navigation.BottomNavDestination
import com.chan.navigation.Routes

object HomeDestination : BottomNavDestination {
    override val route = Routes.HOME.route
    override val title = R.string.home_tab_home
    override val icon = Icons.Default.Home
    override val arguments: List<NamedNavArgument> = emptyList()
}

