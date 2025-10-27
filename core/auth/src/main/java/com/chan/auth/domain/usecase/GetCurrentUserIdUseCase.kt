package com.chan.auth.domain.usecase

import com.chan.auth.domain.AuthRepository
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(
    private val authRepository: AuthRepository
)  {
    operator fun invoke() : String? {
        return authRepository.getCurrentUserId()
    }
}