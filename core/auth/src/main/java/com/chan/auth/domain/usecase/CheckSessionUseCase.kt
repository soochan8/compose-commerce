package com.chan.auth.domain.usecase

import com.chan.auth.domain.AuthRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class CheckSessionUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() : Boolean {
        return authRepository.getSessionFlow().firstOrNull() != null
    }
}