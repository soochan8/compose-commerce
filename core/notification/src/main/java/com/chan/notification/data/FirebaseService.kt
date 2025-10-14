package com.chan.notification.data

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.chan.notification.R
import com.chan.notification.deeplink.DeepLinkManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseService : FirebaseMessagingService() {

    @Inject lateinit var deepLinkManager: DeepLinkManager

    companion object {
        private const val CHANNEL_ID = "commerce_notification_channel"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.data["title"] ?: "알림"
        val body = remoteMessage.data["body"] ?: ""
        val deeplink = remoteMessage.data["deeplink"]

        showNotification(title, body, deeplink)
    }

    private fun showNotification(title: String, body: String, deeplink: String?) {
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager


        val intent = if (!deeplink.isNullOrBlank()) {
            val uri = Uri.parse(deeplink)

            if(deepLinkManager.isSafeDeepLink(uri)) {
                Intent(Intent.ACTION_VIEW, uri).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            } else {
                packageManager.getLaunchIntentForPackage(packageName)?.apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            }
        } else {
            packageManager.getLaunchIntentForPackage(packageName)?.apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        }

        val requestCode = deeplink?.hashCode() ?: System.currentTimeMillis().toInt()
        val pendingIntent = PendingIntent.getActivity(
            this,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.commerce_logo)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder)
    }
}