package com.chan.cart.model

data class CartInTobBarModel(
    val id: String,
    val title: String
)

enum class DeliveryType(val title: String) {
    NORMAL("일반 배송"),
    TODAY("오늘 드림"),
    PICKUP("픽업")
}