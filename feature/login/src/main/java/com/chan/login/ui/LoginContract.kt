package com.chan.login.ui

import com.chan.android.LoadingState
import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState

class LoginContract {

    sealed class Event : ViewEvent {
        sealed class AppLoginEvent : Event() {
            data class UserId(val id: String) : AppLoginEvent()
            data class UserPassword(val password: String) : AppLoginEvent()
            data class AutoLoginChanged(val isChecked: Boolean) : AppLoginEvent()
            data class SaveIdChanged(val isChecked: Boolean) : AppLoginEvent()
            object OnAppLoginButtonClicked : AppLoginEvent()
            object RegisterUser : AppLoginEvent()
        }
        sealed class KakaoLoginEvent : Event() {
            object OnKakaoLoginButtonClicked : KakaoLoginEvent()
        }
        object CheckUserSession : Event()
    }

    data class State(
        val id: String = "",
        val password: String = "",
        val isAutoLoginChecked: Boolean = true,
        val isSaveIdChecked: Boolean = true,
        val isSessionCheckCompleted: Boolean = false,
        val loadingState: LoadingState = LoadingState.Idle
    ) : ViewState

    sealed class Effect : ViewEffect {
        object NavigateToHome : Effect()
        data class ShowError(val errorMsg: String) : Effect()
    }
}