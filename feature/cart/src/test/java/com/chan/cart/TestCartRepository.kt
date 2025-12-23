package com.chan.cart

import com.chan.cart.domain.CartRepository
import com.chan.cart.proto.CartItem
import com.chan.domain.ProductsVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.collections.map
import kotlin.collections.mapNotNull
import kotlin.collections.plus

class TestCartRepository : CartRepository {

    private val cartItems =
        MutableStateFlow<List<CartItem>>(emptyList())

    override suspend fun getProductInfo(productId: String): ProductsVO {
        return ProductsVO(
            productId = productId,
            productName = "test-product",
            brandName = "test-brandName",
            imageUrl = "test-imageUrl",
            originalPrice = 5000,
            discountPercent = 10,
            discountPrice = 4500,
            tags = emptyList(),
            reviewRating = 4.5f,
            reviewCount = 4,
            categoryIds = emptyList()
        )
    }

    override fun getCartItems(deliveryType: Int): Flow<List<CartItem>> = cartItems

    override suspend fun addProductToCart(productId: String) {
        val existing = cartItems.value.find { it.productId == productId }

        cartItems.value =
            if (existing != null) {
                cartItems.value.map {
                    if (it.productId == productId) {
                        it.toBuilder()
                            .setQuantity(it.quantity + 1)
                            .build()
                    } else it
                }
            } else {
                cartItems.value + CartItem.newBuilder()
                    .setProductId(productId)
                    .setProductName("test-product")
                    .setDiscountedPrice(4500)
                    .setQuantity(1)
                    .setIsSelected(true)
                    .setDeliveryType(0)
                    .build()
            }
    }

    override suspend fun removeProductFromCart(productId: String) {
        cartItems.value =
            cartItems.value.filterNot { it.productId == productId }
    }

    override suspend fun updateProductSelected(
        productId: String,
        isSelected: Boolean,
    ) {
        cartItems.value =
            cartItems.value.map {
                if (it.productId == productId) {
                    it.toBuilder().setIsSelected(isSelected).build()
                } else it
            }
    }

    override suspend fun increaseProductQuantity(productId: String) {
        cartItems.value =
            cartItems.value.map {
                if (it.productId == productId) {
                    it.toBuilder().setQuantity(it.quantity + 1).build()
                } else it
            }
    }

    override suspend fun decreaseProductQuantity(productId: String) {
        cartItems.value =
            cartItems.value.mapNotNull {
                if (it.productId == productId) {
                    val newQuantity = it.quantity - 1
                    if (newQuantity > 0) {
                        it.toBuilder()
                            .setQuantity(newQuantity)
                            .build()
                    } else {
                        null // 수량 0이면 제거
                    }
                } else {
                    it
                }
            }
    }

    override suspend fun updateAllProductsSelected(isSelected: Boolean) {
        cartItems.value =
            cartItems.value.map {
                it.toBuilder().setIsSelected(isSelected).build()
            }
    }

    fun setCartItem(items: List<CartItem>) {
        cartItems.value = items
    }
}