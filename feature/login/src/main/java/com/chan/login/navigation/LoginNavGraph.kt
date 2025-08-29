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
        navGraphBuilder.composable(LoginDestination.route) {
            LoginRoute(navController)
        }
    }
}


@Composable
fun LoginRoute(navController: NavHostController) {
    val viewModel: LoginViewModel = hiltViewModel()
    val state by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.setEvent(LoginContract.Event.CheckUserSession)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                LoginContract.Effect.NavigateToHome -> {
                    navController.navigate(
                        Routes.MYPAGE.route
                    ) {
                        popUpTo(LoginDestination.route) {
                            inclusive = true
                        }
                    }
                }

                is LoginContract.Effect.ShowError -> {
                    val errorMsg = context.getString(effect.errorMsg)
                    Log.e("LoginErrorInfo", errorMsg)
                }
            }
        }
    }

    if (state.isSessionCheckCompleted)
        LoginScreen(
            state = state,
            onEvent = viewModel::setEvent
        )
}