package com.chan.feature.ui

import com.chan.core.base.ViewEffect
import com.chan.core.base.ViewEvent
import com.chan.core.base.ViewState
import com.chan.feature.ui.test.UserModel

class TestContract {

    sealed class Event : ViewEvent {
        object OnItemClick : Event()
    }

    data class State(
        val user: UserModel,
        val isLoading: Boolean,
        val isError: Boolean
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class ShowToast(val message: String) : Effect()
    }
}