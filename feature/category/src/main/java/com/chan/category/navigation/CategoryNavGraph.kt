package com.chan.category.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.chan.category.ui.composables.CategoryScreen
import com.chan.navigation.NavGraphProvider
import javax.inject.Inject

class CategoryNavGraph @Inject constructor() : NavGraphProvider {
    override fun addGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(CategoryDestination.route) {
            CategoryScreen()
        }
    }
}