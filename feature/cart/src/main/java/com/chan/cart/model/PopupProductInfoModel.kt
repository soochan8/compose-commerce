package com.chan.cart.model

data class PopupProductInfoModel(
    val productId: String,
    val productName: String,
    val imageUrl: String
) {
    companion object {
        val EMPTY = PopupProductInfoModel(
            productId = "",
            productName = "",
            imageUrl = ""
        )
    }
}