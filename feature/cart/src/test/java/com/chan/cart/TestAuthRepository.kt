package com.chan.cart

import com.chan.auth.domain.AuthRepository
import com.chan.auth.domain.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TestAuthRepository : AuthRepository {
    private val sessionFlow = MutableStateFlow<UserSession?>(null)

    override fun getSessionFlow(): Flow<UserSession?> = sessionFlow

    override suspend fun login(userId: String, token: String) {
        sessionFlow.value = UserSession(userId, token)
    }

    override suspend fun logout() {
        sessionFlow.value = null
    }

    override fun getCurrentUserId(): String? {
        return sessionFlow.value?.userId
    }

    // 테스트 전용 훅 메서드
    fun setSession(session: UserSession?) {
        sessionFlow.value = session
    }

    fun setSession(userId: String, token: String) {
        sessionFlow.value = UserSession(userId, token)
    }

    fun clearSession() {
        sessionFlow.value = null
    }
}