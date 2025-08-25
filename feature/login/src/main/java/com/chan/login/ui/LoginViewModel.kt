package com.chan.login.ui

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.android.LoadingState
import com.chan.auth.domain.AuthRepository
import com.chan.login.domain.KakaoLoginManager
import com.chan.login.domain.KakaoLoginResult
import com.chan.login.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val authRepository: AuthRepository,
    private val kakaoLoginManager: KakaoLoginManager
) : BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {

    override fun setInitialState() = LoginContract.State()

    override fun handleEvent(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.AppLoginEvent -> handleAppLoginEvents(event)
            is LoginContract.Event.KakaoLoginEvent -> loginWithKakao()
        }
    }

    private fun handleAppLoginEvents(event: LoginContract.Event.AppLoginEvent) {
        when (event) {
            is LoginContract.Event.AppLoginEvent.UserId -> setState { copy(id = event.id) }
            is LoginContract.Event.AppLoginEvent.UserPassword -> setState { copy(password = event.password) }
            is LoginContract.Event.AppLoginEvent.AutoLoginChanged -> setState {
                copy(
                    isAutoLoginChecked = event.isChecked
                )
            }

            is LoginContract.Event.AppLoginEvent.SaveIdChanged -> setState { copy(isSaveIdChecked = event.isChecked) }
            is LoginContract.Event.AppLoginEvent.OnAppLoginButtonClicked -> loginWithApp(
                viewState.value.id,
                viewState.value.password
            )

            is LoginContract.Event.AppLoginEvent.RegisterUser -> registerUser(
                viewState.value.id,
                viewState.value.password
            )
        }
    }

    private fun registerUser(userId: String, userPw: String) {
        if (userId.isNotEmpty() && userPw.isNotEmpty()) {
            viewModelScope.launch {
                setState { copy(loadingState = LoadingState.Loading) }
                loginRepository.registerUser(userId, userPw)
                    .onSuccess {
                        setState { copy(loadingState = LoadingState.Success) }
                    }
                    .onFailure { error ->
                        setState { copy(loadingState = LoadingState.Error) }
                        setEffect { LoginContract.Effect.ShowError(error.message ?: "회원가입 실패") }
                    }
            }
        }
    }

    private fun loginWithApp(userId: String, userPw: String) {
        if (userId.isNotEmpty() && userPw.isNotEmpty()) {
            viewModelScope.launch {
                setState { copy(loadingState = LoadingState.Loading) }

                loginRepository.appLogin(userId, userPw)
                    .onSuccess {
                        setState { copy(loadingState = LoadingState.Success) }

                        val dummyToken = UUID.nameUUIDFromBytes(userId.toByteArray()).toString()
                        authRepository.login(userId, dummyToken)

                        setEffect { LoginContract.Effect.NavigateToHome }
                    }
                    .onFailure { error ->
                        setState { copy(loadingState = LoadingState.Error) }
                        setEffect { LoginContract.Effect.ShowError(error.message ?: "로그인 실패") }
                    }
            }

        }
    }

    private fun loginWithKakao() {
        setState { copy(loadingState = LoadingState.Loading) }
        kakaoLoginManager.login()
            .onEach { result ->
                when (result) {
                    is KakaoLoginResult.Success -> handleKakaoLoginSuccess()
                    is KakaoLoginResult.Error -> handleLoginError("카카오 로그인 실패")
                    KakaoLoginResult.Cancelled -> {
                        setState { copy(loadingState = LoadingState.Idle) }
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun handleKakaoLoginSuccess() {
        setState { copy(loadingState = LoadingState.Success) }
        setEffect { LoginContract.Effect.NavigateToHome }
    }

    private fun handleLoginError(errorMsg: String) {
        setState { copy(loadingState = LoadingState.Error) }
        setEffect { LoginContract.Effect.ShowError(errorMsg) }
    }
} 