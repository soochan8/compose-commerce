package com.chan.cart.data

import com.chan.auth.domain.AuthRepository
import com.chan.cart.data.mapper.toProductsVO
import com.chan.cart.domain.CartRepository
import com.chan.database.dao.CartDao
import com.chan.database.dao.ProductsDao
import com.chan.domain.ProductsVO
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val productsDao: ProductsDao,
    private val cartDao: CartDao,
    private val authRepository: AuthRepository
) : CartRepository {

    private fun requireUserId(): String {
        return authRepository.getCurrentUserId()
            ?: throw IllegalStateException("로그인이 필요합니다")
    }

    override suspend fun getProductInfo(productId: String): ProductsVO {
        return productsDao.getProductsByProductId(productId).toProductsVO()
    }

    override suspend fun addToCart(productId: String) {
        val userId = requireUserId()
        cartDao.increaseQuantity(
            userId = userId,
            productId = productId,
        )
    }

    override suspend fun getInCartProducts(): List<ProductsVO> {
        val userId = requireUserId()
        return cartDao.getInCartProducts(userId).map { it.toProductsVO() }
    }
}