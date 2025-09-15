package com.chan.product.ui

import com.chan.android.LoadingState
import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.product.ui.model.ProductDetailModel

class ProductDetailContract {
    sealed class Event : ViewEvent {
        data class OnCouponDownloadClick(val couponId: String) : Event()
        data class OnCartPopupClick(val productId: String) : Event()
        data class OnCartClick(val productId: String) : Event()
    }

    data class State(
        val productDetailInfo: ProductDetailModel? = null,
        val loadingState: LoadingState = LoadingState.Idle
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class ShowError(val message: String) : Effect()
        data class ShowToast(val message: String) : Effect()
        data class UpdateWebView(val couponId: String) : Effect()
        data class RevertWebViewButton(val couponId: String) : Effect()

        sealed class Navigation : Effect() {
            data class ToCartPopupRoute(val productId: String) : Effect()
            data class ToCartRoute(val productId: String) : Effect()
        }
    }
}