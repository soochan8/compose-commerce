package com.chan.home.composables.home.banner

import android.webkit.JavascriptInterface
import com.chan.home.home.HomeContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @param onCouponClicked 웹의 '쿠폰 받기' 버튼 클릭 시 호출
 */
class WebViewInterface(
    private val scope: CoroutineScope,
    private val onCouponClicked: (HomeContract.Event.Banner) -> Unit
) {

    @JavascriptInterface
    fun onCouponClicked(couponId: String) {
        scope.launch {
            onCouponClicked(HomeContract.Event.Banner.OnCouponClick(couponId))
        }
    }
}

