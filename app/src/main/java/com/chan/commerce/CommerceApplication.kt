package com.chan.commerce

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CommerceApplication : Application() {

    companion object {
        private const val CHANNEL_ID = "commerce_notification_channel"
        private const val CHANNEL_NAME = "Commerce Notification"
    }


    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(com.chan.login.R.string.kakao_native_app_key))

        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
