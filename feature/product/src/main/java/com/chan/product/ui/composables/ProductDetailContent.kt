package com.chan.product.ui.composables

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.chan.android.ui.theme.Radius
import com.chan.android.ui.theme.Spacing
import com.chan.product.ui.ProductDetailContract
import com.chan.product.ui.WebAppInterface
import kotlinx.coroutines.flow.Flow


private const val JAVASCRIPT_INTERFACE_NAME = "AndroidBridge"

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ProductDetailContent(
    state: ProductDetailContract.State,
    onEvent: (ProductDetailContract.Event) -> Unit,
    effect: Flow<ProductDetailContract.Effect>
) {
    var isExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var currentToast by remember { mutableStateOf<Toast?>(null) }
    var webView by remember { mutableStateOf<WebView?>(null) }

    DisposableEffect(webView) {
        onDispose {
            webView?.destroy()
        }
    }

    LaunchedEffect(Unit) {
        effect.collect { effect ->
            when (effect) {
                is ProductDetailContract.Effect.UpdateWebView -> {
                    webView?.loadUrl("javascript:markCouponAsDownloaded('${effect.couponId}')")
                }

                is ProductDetailContract.Effect.RevertWebViewButton -> {
                    webView?.loadUrl("javascript:revertCouponButton('${effect.couponId}')")
                }

                is ProductDetailContract.Effect.ShowToast -> {
                    currentToast?.cancel()
                    currentToast = Toast.makeText(context, effect.message, Toast.LENGTH_SHORT)
                    currentToast?.show()
                }

                is ProductDetailContract.Effect.ShowError -> Log.d(
                    "ProductDetailInfo",
                    effect.message
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WebViewComponent(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (isExpanded) {
                        Modifier.wrapContentHeight()
                    } else {
                        Modifier.height(1000.dp)
                    }
                ),
            onWebViewReady = { webView = it },
            onDownloadClick = { couponId ->
                onEvent(ProductDetailContract.Event.OnCouponDownloadClick(couponId))
            }
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            color = Color.White,
            tonalElevation = 4.dp
        ) {
            ExpandToggleButton(
                isExpanded = isExpanded,
                onClick = { isExpanded = !isExpanded }
            )
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun WebViewComponent(
    modifier: Modifier = Modifier,
    onWebViewReady: (WebView) -> Unit,
    onDownloadClick: (String) -> Unit
) {
    val localProductDetailFilePath = "product_detail.html"
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                isVerticalScrollBarEnabled = true
                settings.cacheMode = WebSettings.LOAD_DEFAULT
                addJavascriptInterface(
                    WebAppInterface(onDownloadClick),
                    JAVASCRIPT_INTERFACE_NAME
                )
                loadUrl("file:///android_asset/$localProductDetailFilePath")
                onWebViewReady(this)
            }
        }
    )
}

@Composable
private fun ExpandToggleButton(
    isExpanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(Spacing.spacing4),
        shape = RoundedCornerShape(Radius.radius1),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Text(if (isExpanded) "간략히 보기" else "상품정보 더보기")
    }
}