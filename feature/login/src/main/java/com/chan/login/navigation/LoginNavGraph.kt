package com.chan.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.chan.login.ui.composables.LoginScreen
import com.chan.navigation.NavGraphProvider
import javax.inject.Inject

class LoginNavGraph @Inject constructor() : NavGraphProvider {
    override fun addGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(LoginDestination.route) {
            LoginScreen()
        }
    }
}