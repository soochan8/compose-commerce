package com.chan.search.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.chan.navigation.NavGraphProvider
import com.chan.search.ui.composables.SearchScreen
import javax.inject.Inject

class SearchNavGraph @Inject constructor() : NavGraphProvider {
    override fun addGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(
            route = SearchDestination.route,
            arguments = SearchDestination.arguments
        ) { backStackEntry ->
            SearchScreen(
                navController = navController
            )
        }
    }
}