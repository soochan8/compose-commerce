package com.chan.notification.deeplink

sealed class DeepLinkDestination {
    data class WebView(val url: String) : DeepLinkDestination()
}