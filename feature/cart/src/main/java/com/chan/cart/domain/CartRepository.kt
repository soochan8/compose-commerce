package com.chan.cart.domain

import com.chan.domain.ProductsVO

interface CartRepository {
    suspend fun getProductInfo(productId: String) : ProductsVO
    suspend fun addToCart(productId: String)
    suspend fun getInCartProducts() : List<ProductsVO>
}