package com.chan.cart.data

import android.content.Context
import androidx.datastore.core.DataStore
import com.chan.auth.domain.usecase.GetCurrentUserIdUseCase
import com.chan.cart.data.datastore.CartDataStoreManager
import com.chan.cart.data.mapper.toProductsVO
import com.chan.cart.domain.CartRepository
import com.chan.cart.proto.Cart
import com.chan.cart.proto.CartItem
import com.chan.database.dao.ProductsDao
import com.chan.domain.ProductsVO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val productsDao: ProductsDao,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : CartRepository {

    private fun getCartStore(): DataStore<Cart> {
        val userId = getCurrentUserIdUseCase() ?: "guest"
        return CartDataStoreManager.getDataStore(context, userId)
    }

    override suspend fun getProductInfo(productId: String): ProductsVO {
        return productsDao.getProductsByProductId(productId)?.toProductsVO()
            ?: throw NoSuchElementException("Product not found with id: $productId")
    }

    override fun getCartItems(): Flow<List<CartItem>> {
        return getCartStore().data.map { it.itemsList }
    }

    override suspend fun addProductToCart(productId: String) {
        getCartStore().updateData { cart ->
            val existingItem = cart.itemsList.find { it.productId == productId }

            if (existingItem != null) {
                val updatedItems = cart.itemsList.map {
                    if (it.productId == productId) {
                        it.toBuilder().setQuantity(it.quantity + 1).build()
                    } else {
                        it
                    }
                }
                cart.toBuilder().clearItems().addAllItems(updatedItems).build()
            } else {
                val productInfo = productsDao.getProductsByProductId(productId)
                if (productInfo != null) {
                    val newItem = CartItem.newBuilder()
                        .setProductId(productId)
                        .setProductName(productInfo.productName)
                        .setImageUrl(productInfo.imageUrl)
                        .setOriginPrice(productInfo.originalPrice)
                        .setDiscountedPrice(productInfo.discountPrice)
                        .setQuantity(1)
                        .setIsSelected(true)
                        .build()
                    cart.toBuilder().addItems(newItem).build()
                } else {
                    cart
                }
            }
        }
    }

    override suspend fun removeProductFromCart(productId: String) {
        updateCartItems { items ->
            items.filterNot { it.productId == productId }
        }
    }

    override suspend fun updateProductSelected(productId: String, isSelected: Boolean) {
        updateCartItems { items ->
            items.map {
                if (it.productId == productId) {
                    it.toBuilder().setIsSelected(isSelected).build()
                } else {
                    it
                }
            }
        }
    }

    override suspend fun increaseProductQuantity(productId: String) {
        updateCartItems { items ->
            items.map {
                if (it.productId == productId) {
                    it.toBuilder().setQuantity(it.quantity + 1).build()
                } else {
                    it
                }
            }
        }
    }

    override suspend fun decreaseProductQuantity(productId: String) {
        getCartStore().updateData { cart ->
            val targetItem = cart.itemsList.find { it.productId == productId } ?: return@updateData cart

            val updatedItems = if (targetItem.quantity > 1) {
                cart.itemsList.map {
                    if (it.productId == productId) {
                        it.toBuilder().setQuantity(it.quantity - 1).build()
                    } else {
                        it
                    }
                }
            } else {
                cart.itemsList.filterNot { it.productId == productId }
            }
            cart.toBuilder().clearItems().addAllItems(updatedItems).build()
        }
    }

    override suspend fun updateAllProductsSelected(isSelected: Boolean) {
        updateCartItems { items ->
            items.map {
                it.toBuilder().setIsSelected(isSelected).build()
            }
        }
    }

    private suspend fun updateCartItems(transform: (List<CartItem>) -> List<CartItem>) {
        getCartStore().updateData { cart ->
            val originalItems = cart.itemsList
            val updatedItems = transform(originalItems) // 로직 실행
            cart.toBuilder().clearItems().addAllItems(updatedItems).build()
        }
    }

}
