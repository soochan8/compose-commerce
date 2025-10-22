package com.chan.auth.domain.usecase

import com.chan.auth.domain.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Flow 구독용
class FlowCurrentUserIdUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<String?> =
        authRepository.getSessionFlow()
            .map { it?.userId }
            .distinctUntilChanged()
}