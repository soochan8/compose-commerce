package com.chan.login.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.appTypography
import com.chan.login.R
import com.chan.login.ui.LoginContract
import com.chan.login.ui.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { collect ->
            when (collect) {
                LoginContract.Effect.NavigateToHome -> {}
                is LoginContract.Effect.ShowError -> Log.e("LoginErrorInfo", collect.errorMsg)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.olive_young),
            style = MaterialTheme.appTypography.appTitle,
            modifier = Modifier.padding(top = 50.dp)
        )

        Spacer(modifier = Modifier.height(Spacing.spacing7))

        AppLogin(
            state = state,
            onEvent = viewModel::setEvent
        )
        KakaoLogin(onKakaoLogin = { viewModel.setEvent(LoginContract.Event.KakaoLoginEvent.OnKakaoLoginButtonClicked) })
    }
}