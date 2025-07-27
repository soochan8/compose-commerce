package com.chan.login.ui

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.android.LoadingState
import com.chan.login.R
import com.chan.login.domain.KakaoLoginManager
import com.chan.login.domain.KakaoLoginResult
import com.chan.login.domain.repository.LoginRepository
import com.chan.login.domain.vo.UserVO
import com.chan.login.util.SecurityUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
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
                try {
                    if (loginRepository.findUserByUserId(userId) != null) {
                        setState { copy(loadingState = LoadingState.Error) }
                        setEffect { LoginContract.Effect.ShowError(R.string.error_user_already_exists) }
                        return@launch
                    }

                    val salt = SecurityUtils.generateSalt()
                    val hashedPassword = SecurityUtils.hashPassword(
                        password = userPw.toCharArray(),
                        salt = salt
                    )
                    val newUser = UserVO(
                        userId = userId,
                        hashedPassword = hashedPassword,
                        salt = salt
                    )

                    loginRepository.registerUser(user = newUser)
                    setState { copy(loadingState = LoadingState.Success) }
                    setEffect { LoginContract.Effect.ShowError(R.string.success_register) }


                } catch (e: Exception) {
                    setState { copy(loadingState = LoadingState.Error) }
                    setEffect { LoginContract.Effect.ShowError(R.string.error_unknown) }
                }
            }
        }
    }

    private fun loginWithApp(userId: String, userPw: String) {
        if (userId.isNotEmpty() && userPw.isNotEmpty()) {
            viewModelScope.launch {
                setState { copy(loadingState = LoadingState.Loading) }
                try {
                    val storedUser = loginRepository.findUserByUserId(userId)
                    if (storedUser == null) {
                        setState { copy(loadingState = LoadingState.Error) }
                        setEffect { LoginContract.Effect.ShowError(R.string.error_user_not_found) }
                        return@launch
                    }

                    val isPasswordCorrect = SecurityUtils.verifyPassword(
                        password = userPw.toCharArray(),
                        salt = storedUser.salt,
                        hashedPassword = storedUser.hashedPassword
                    )

                    if (isPasswordCorrect) {
                        setState { copy(loadingState = LoadingState.Success) }
                        setEffect { LoginContract.Effect.NavigateToHome }
                    } else {
                        setState { copy(loadingState = LoadingState.Error) }
                        setEffect { LoginContract.Effect.ShowError(R.string.error_password_mismatch) }
                    }
                } catch (e: Exception) {
                    setState { copy(loadingState = LoadingState.Error) }
                    setEffect { LoginContract.Effect.ShowError(R.string.error_unknown) }
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
                    is KakaoLoginResult.Error -> handleLoginError(R.string.login_failed)
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

    private fun handleLoginError(@StringRes messageResId: Int) {
        setState { copy(loadingState = LoadingState.Error) }
        setEffect { LoginContract.Effect.ShowError(messageResId = messageResId) }
    }
} 