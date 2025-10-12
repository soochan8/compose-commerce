package com.chan.home.composables.home.banner

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.chan.home.home.HomeContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance

private const val JAVASCRIPT_INTERFACE_NAME = "AndroidApp"

@Composable
fun HomeBannerWebViewScreen(
    url: String,
    onEvent: (HomeContract.Event.Banner) -> Unit,
    effect: Flow<HomeContract.Effect>,
) {
    val updatedOnDownloadClick by rememberUpdatedState(onEvent)
    val scope = rememberCoroutineScope { Dispatchers.Main }
    val context = LocalContext.current
    val webView = remember(context) {
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()

            addJavascriptInterface(
                WebViewInterface(scope, updatedOnDownloadClick),
                JAVASCRIPT_INTERFACE_NAME
            )
        }
    }

    LaunchedEffect(url) {
        if (webView.url != url)
            webView.loadUrl(url)
    }

    LaunchedEffect(Unit) {
        effect
            .filterIsInstance<HomeContract.Effect.Banner>()
            .collect { bannerEffect ->
                when (bannerEffect) {
                    HomeContract.Effect.Banner.ShowCouponDownloaded -> webView.evaluateJavascript(
                        "onCouponDownloadSuccess()",
                        null
                    )
                }
            }
    }

    AndroidView(
        factory = { webView },
        modifier = Modifier.fillMaxSize()
    )
}