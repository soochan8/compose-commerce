package com.chan.login.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.chan.login.ui.LoginContract
import com.chan.login.ui.LoginViewModel
import com.chan.login.ui.composables.LoginScreen
import com.chan.navigation.NavGraphProvider
import com.chan.navigation.Routes
import javax.inject.Inject

class LoginNavGraph @Inject constructor() : NavGraphProvider {
    override fun addGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(
            route = LoginDestination.route,
            arguments = LoginDestination.arguments
        ) { backStackEntry ->
            val redirectRoute = backStackEntry.arguments?.getString("redirect") ?: ""

            LoginRoute(navController, redirectRoute)
        }
    }
}


@Composable
fun LoginRoute(navController: NavHostController, redirectRoute: String) {
    val viewModel: LoginViewModel = hiltViewModel()
    val state by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                LoginContract.Effect.NavigateToHome -> {
                    val target =
                        if (redirectRoute.isNotEmpty()) redirectRoute else Routes.MYPAGE.route
                    navController.navigate(target) {
                        popUpTo(Routes.LOGIN.route) { inclusive = true }
                    }
                }

                is LoginContract.Effect.ShowError -> {
                    val errorMsg = context.getString(effect.errorMsg)
                    Log.e("LoginErrorInfo", errorMsg)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(LoginContract.Event.CheckUserSession)
    }


    if (state.isSessionCheckCompleted) {
        LoginScreen(
            state = state,
            onEvent = viewModel::setEvent
        )
    }
}