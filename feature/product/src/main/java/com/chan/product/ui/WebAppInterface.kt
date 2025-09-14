package com.chan.product.ui

import android.webkit.JavascriptInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @param onDownloadClick 웹의 '쿠폰 받기' 버튼 클릭 시 호출
 */
class WebAppInterface(
    private val scope: CoroutineScope,
    private val onDownloadClick: (String) -> Unit
) {

    /**
     * html downloadCoupon 클릭 시 호출
     */
    @JavascriptInterface
    fun downloadCoupon(couponId: String) {
        scope.launch {
            onDownloadClick(couponId)
        }
    }
}

