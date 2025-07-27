package com.chan.product.ui.composables

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.chan.android.ui.theme.Radius
import com.chan.android.ui.theme.Spacing

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ProductDetailContent() {
    var isExpanded by remember { mutableStateOf(false) }

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
                )
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
private fun WebViewComponent(modifier: Modifier = Modifier) {
    val localProductDetailFilePath = "product_detail.html"
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.cacheMode = WebSettings.LOAD_DEFAULT
                isVerticalScrollBarEnabled = true
            }
        },
        update = {
            it.loadUrl("file:///android_asset/$localProductDetailFilePath")
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