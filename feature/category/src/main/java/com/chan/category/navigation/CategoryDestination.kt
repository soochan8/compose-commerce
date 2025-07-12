package com.chan.category.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.navigation.NamedNavArgument
import com.chan.category.R
import com.chan.navigation.BottomNavDestination
import com.chan.navigation.Routes

object CategoryDestination : BottomNavDestination {
    override val route = Routes.CATEGORY.route
    override val title = R.string.category
    override val icon = Icons.Default.Menu
    override val arguments: List<NamedNavArgument> = emptyList()
}