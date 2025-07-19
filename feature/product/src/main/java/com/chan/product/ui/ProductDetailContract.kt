package com.chan.product.ui

import com.chan.android.LoadingState
import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.product.ui.model.ProductDetailModel

class ProductDetailContract {
    sealed class Event : ViewEvent {
        object ProductDetailLoad : Event()
    }

    data class State(
        val productDetailInfo: ProductDetailModel? = null,
        val loadingState: LoadingState = LoadingState.Idle
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class ShowError(val message: String) : Effect()
    }
}