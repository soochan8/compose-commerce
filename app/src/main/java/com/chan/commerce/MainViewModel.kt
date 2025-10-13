package com.chan.commerce

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _deepLinkNavigation = MutableSharedFlow<String>()
    val deepLinkNavigation = _deepLinkNavigation.asSharedFlow()

    fun handleDeepLink(route: String) {
        viewModelScope.launch {
            val uri = Uri.parse(route)
            when (uri.host) {
                "coupon" -> {
                    val path = uri.lastPathSegment

                    //임시 html 파일 경로로 이동 (webView)
                    val url = "file:///android_asset/event.html"
                    _deepLinkNavigation.emit(url)
                }
                else -> {
                    Log.w("FCMTEST", "Unknown deeplink host: ${uri.host}")
                }
            }
        }
    }
}
