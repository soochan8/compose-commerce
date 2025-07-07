package com.chan.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface NavDestination {
    val route: String
    @get:StringRes val title: Int
    val icon: ImageVector
    val arguments: List<NamedNavArgument>
}

//공통 NavDestination 관리
interface NavDestinationProvider {
    fun getDestination(): List<NavDestination>
}

interface NavGraphProvider {
    fun addGraph(navGraphBuilder: NavGraphBuilder, navController: NavHostController)
}