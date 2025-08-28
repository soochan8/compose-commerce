package com.chan.login.data.repository

import android.content.Context
import com.chan.login.domain.KakaoLoginManager
import com.chan.login.domain.KakaoLoginResult
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class KakaoLoginManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : KakaoLoginManager {

    override fun login(): Flow<KakaoLoginResult> = callbackFlow {
        val sendResult = { result: KakaoLoginResult ->
            trySend(result)
            close()
        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            when {
                error != null -> {
                    //취소
                    val result =
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            KakaoLoginResult.Cancelled
                        } else {
                            //로그인 실패
                            KakaoLoginResult.Error(error.message ?: "KaKao Login Error")
                        }
                    sendResult(result)
                }
                //로그인 성공
                token != null -> {
                    UserApiClient.instance.me { user, meError ->
                        val result = if (user != null) {
                            KakaoLoginResult.Success(user.id.toString(), token.accessToken)
                        } else {
                            KakaoLoginResult.Error(error?.message ?: "KaKao Login Error")
                        }
                        sendResult(result)
                    }
                }
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    //로그인을 취소한 경우
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        //
                        sendResult(KakaoLoginResult.Cancelled)
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    //카카오톡에 연결된 계정이 없는 경우
                    callback(token, null)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }

        awaitClose { }
    }
} 