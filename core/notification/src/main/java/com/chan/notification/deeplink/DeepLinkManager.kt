package com.chan.notification.deeplink

import android.net.Uri
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeepLinkManager @Inject constructor() {

    companion object {
        private const val DEEPLINK_SCHEME = "commerce_app"
        private val DEEPLINK_HOSTS = setOf("webView")
        private val DEEPLINK_WEB_DOMAINS = setOf("https://www.mycommerce.com")
        private const val LOCAL_ASSET_PREFIX = "file:///android_asset/"
    }

    fun parse(uri: Uri?): DeepLinkDestination? {
        if (uri == null || uri.scheme != DEEPLINK_SCHEME) return null
        if (!verificationHosts(uri.host)) return null

        return when (uri.host) {
            "webView" -> {
                val url = uri.getQueryParameter("url")
                if (url.isNullOrBlank()) {
                    null
                } else {
                    if(isAllowedWebUrl(url))
                        DeepLinkDestination.WebView(url)
                    else {
                        Log.e("DeepLink", "Invalid deeplink blocked: $uri")
                        null
                    }
                }
            }
            else -> {
                null
            }
        }
    }

    fun isAllowedWebUrl(url: String): Boolean {
        return when {
            DEEPLINK_WEB_DOMAINS.any { domain -> url.startsWith(domain) } -> true
            url.startsWith(LOCAL_ASSET_PREFIX) -> true
            else -> false
        }
    }

    private fun verificationHosts(host: String?) = DEEPLINK_HOSTS.contains(host)

    fun isSafeDeepLink(uri: Uri) = uri.scheme == DEEPLINK_SCHEME
}