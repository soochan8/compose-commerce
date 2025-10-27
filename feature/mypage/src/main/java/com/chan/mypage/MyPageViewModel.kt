package com.chan.mypage

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.mypage.MyPageContract.Effect.Navigation.ToHome
import com.chan.mypage.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel<MyPageContract.Event, MyPageContract.State, MyPageContract.Effect>() {
    override fun setInitialState() = MyPageContract.State()

    override fun handleEvent(event: MyPageContract.Event) {
        when (event) {
            MyPageContract.Event.OnLogoutClicked -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            setEffect { ToHome }
        }
    }
}