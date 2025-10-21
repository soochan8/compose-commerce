package com.chan.commerce

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chan.notification.deeplink.DeepLinkDestination
import com.chan.notification.deeplink.DeepLinkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val deepLinkManager: DeepLinkManager
) : ViewModel() {

    private val _deepLinkNavigation = MutableSharedFlow<String>(replay = 1)
    val deepLinkNavigation = _deepLinkNavigation.asSharedFlow()

    fun handleDeepLink(route: String) {
        viewModelScope.launch {
            val uri = Uri.parse(route)
            val destination = deepLinkManager.parse(uri)
            when (destination) {
                is DeepLinkDestination.WebView -> {
                    _deepLinkNavigation.emit(destination.url)
                }
                null -> {
                    Log.e("DeepLink", "Invalid or unsupported deeplink $uri")
                }
            }
        }
    }
}

