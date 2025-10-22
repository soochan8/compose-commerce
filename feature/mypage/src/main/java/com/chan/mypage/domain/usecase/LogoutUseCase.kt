package com.chan.mypage.domain.usecase

import android.content.Context
import com.chan.auth.domain.AuthRepository
import com.chan.database.datastore.CartDataStoreManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.logout()
        CartDataStoreManager.clearAll()
    }
}