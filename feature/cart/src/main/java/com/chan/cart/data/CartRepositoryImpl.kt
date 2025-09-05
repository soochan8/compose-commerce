package com.chan.cart.data

import com.chan.auth.domain.AuthRepository
import com.chan.cart.data.mapper.toCartProductVO
import com.chan.cart.data.mapper.toProductsVO
import com.chan.cart.domain.CartRepository
import com.chan.database.dao.CartDao
import com.chan.database.dao.ProductsDao
import com.chan.domain.CartProductVO
import com.chan.domain.ProductsVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun getInCartProducts(): Flow<List<CartProductVO>> {
        val userId = requireUserId()
        return cartDao.getInCartProducts(userId)
            .map { list -> list.map { it.toCartProductVO() } }
    }

    override suspend fun updateProductSelected(
        productId: String,
        isSelected: Boolean
    ) {
        val userId = requireUserId()
        cartDao.updateProductSelected(
            userId = userId,
            productId = productId,
            isSelected = isSelected
        )
    }

    override suspend fun increaseProductQuantity(productId: String) {
        val userId = requireUserId()
        cartDao.increaseQuantity(userId, productId)
    }

    override suspend fun decreaseProductQuantity(productId: String) {
        val userId = requireUserId()
        cartDao.decreaseQuantity(userId, productId)
    }

    override suspend fun updateAllProductSelected(isAllSelected: Boolean) {
        val userId = requireUserId()
        cartDao.updateAllProductSelected(userId, isAllSelected)
    }

    override suspend fun deleteCartByProductId(productId: String) {
        val userId = requireUserId()
        cartDao.deleteCartByProductId(userId, productId)
    }
}