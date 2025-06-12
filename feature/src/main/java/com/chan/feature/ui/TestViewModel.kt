package com.chan.feature.ui

import androidx.lifecycle.viewModelScope
import com.chan.core.base.BaseViewModel
import com.chan.feature.domain.usecase.TestUseCase
import com.chan.feature.mapper.toPresentation
import com.chan.feature.ui.test.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val testUserCase: TestUseCase
) :
    BaseViewModel<TestContract.Event, TestContract.State, TestContract.Effect>() {

    override fun setInitialState() = TestContract.State(
        user = UserModel(
            userName = "버튼 클릭"
        ),
        isLoading = true,
        isError = false
    )

    override fun handleEvent(event: TestContract.Event) {
        when (event) {
            is TestContract.Event.OnItemClick -> getUser()
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            val userModel = testUserCase.invoke().toPresentation()

            setState {
                copy(
                    user = userModel,
                    isLoading = false
                )
            }
            setEffect { TestContract.Effect.ShowToast(userModel.userName) }
        }
    }
}