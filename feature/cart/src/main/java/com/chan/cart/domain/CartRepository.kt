package com.chan.cart.domain

import com.chan.domain.CartProductVO
import com.chan.domain.ProductsVO
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getProductInfo(productId: String) : ProductsVO
    suspend fun addToCart(productId: String)
    fun getInCartProducts() : Flow<List<CartProductVO>>
    suspend fun updateProductSelected(productId: String, isSelected: Boolean)
    suspend fun increaseProductQuantity(productId: String)
    suspend fun decreaseProductQuantity(productId: String)
    suspend fun updateAllProductSelected(isAllSelected: Boolean)
}