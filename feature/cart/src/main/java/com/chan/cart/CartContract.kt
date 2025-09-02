package com.chan.cart

import androidx.annotation.StringRes
import com.chan.android.LoadingState
import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.cart.model.CartInProductsModel
import com.chan.cart.model.PopupProductInfoModel

class CartContract {
    sealed class Event : ViewEvent {
        data class LoadPopupProductInfo(val productId: String) : Event()
        object LoadCartProducts : Event()
        data class AddToProduct(val productId: String) : Event()
        data class UpdateProductSelected(val productId: String, val isSelected: Boolean) : Event()
    }

    data class State(
        val popupProductInfo: PopupProductInfoModel = PopupProductInfoModel.EMPTY,
        val cartInProducts: List<CartInProductsModel> = emptyList(),
        val loadingState: LoadingState = LoadingState.Idle
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class ShowError(val errorMsg: String) : Effect()
        data class ShowToast(@StringRes val message: Int) : Effect()
        object DismissCartPopup : Effect()
    }
}