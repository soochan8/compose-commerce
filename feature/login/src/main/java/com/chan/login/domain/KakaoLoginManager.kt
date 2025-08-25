package com.chan.login.domain

import kotlinx.coroutines.flow.Flow

sealed class KakaoLoginResult {
    data class Success(val userId: String, val accessToken: String) : KakaoLoginResult()
    data class Error(val message: String) : KakaoLoginResult()
    object Cancelled : KakaoLoginResult()
}

interface KakaoLoginManager {
    fun login(): Flow<KakaoLoginResult>
} 