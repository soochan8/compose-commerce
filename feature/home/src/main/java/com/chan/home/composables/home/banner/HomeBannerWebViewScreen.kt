package com.chan.home.composables.home.banner

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
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
    var webView by remember { mutableStateOf<WebView?>(null) }

    LaunchedEffect(Unit) {
        effect
            .filterIsInstance<HomeContract.Effect.Banner>()
            .collect { bannerEffect ->
                when (bannerEffect) {
                    HomeContract.Effect.Banner.ShowCouponDownloaded -> webView?.evaluateJavascript(
                        "onCouponDownloadSuccess()",
                        null
                    )
                }
            }
    }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webView = this

                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()

                addJavascriptInterface(
                    WebViewInterface(scope, updatedOnDownloadClick),
                    JAVASCRIPT_INTERFACE_NAME
                )

                loadUrl(url)
            }
        },
        update = {
            if(it.url != url) it.loadUrl(url)
        },
        onRelease = {
            it.apply {
                stopLoading()
                webChromeClient = null
                removeJavascriptInterface(JAVASCRIPT_INTERFACE_NAME)
                destroy()
                webView = null
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}