package com.chan.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.chan.database.dto.ProductWithCartDto
import com.chan.database.entity.cart.CartProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartProduct: CartProductEntity)

    @Update
    suspend fun update(cartProduct: CartProductEntity)

    @Query("DELETE FROM cart_products WHERE userId = :userId AND productId = :productId")
    suspend fun delete(userId: String, productId: String)

    //특정 상품을 장바구니에 담았는 지 확인
    @Query("SELECT * FROM cart_products WHERE userId = :userId AND productId = :productId LIMIT 1")
    suspend fun findCartProduct(userId: String, productId: String): CartProductEntity?


    //장바구니 리스트 조회
    @Query(
        """
    SELECT p.*, c.quantity as cartQuantity, c.isSelected as isSelected
    FROM products p
    JOIN cart_products c ON p.productId = c.productId
    WHERE c.userId = :userId
"""
    )
    fun getInCartProducts(userId: String): Flow<List<ProductWithCartDto>>


    //장바구니 상품 개수 증가
    //없으면 추가, 있으면 +1
    @Transaction
    suspend fun increaseQuantity(userId: String, productId: String) {
        val insertedProduct = findCartProduct(userId, productId)
        if (insertedProduct == null) {
            insert(CartProductEntity(userId = userId, productId = productId, quantity = 1))
        } else {
            val updatedProduct = insertedProduct.copy(quantity = insertedProduct.quantity + 1)
            update(updatedProduct)
        }
    }

    //장바구니 상품 개수 감소
    //있으면 -1, 1개일 경우 삭제
    @Transaction
    suspend fun decreaseQuantity(userId: String, productId: String) {
        val deletedProduct = findCartProduct(userId, productId)
        if (deletedProduct != null) {
            if (deletedProduct.quantity > 1) {
                val updatedProduct =
                    deletedProduct.copy(quantity = deletedProduct.quantity - 1)
                update(updatedProduct)
            } else {
                delete(userId, productId)
            }
        }
    }

    //장바구니 상품 클릭 여부
    @Query(
        """
        UPDATE cart_products
        SET isSelected = :isSelected
        WHERE userId = :userId AND productId = :productId
    """
    )
    suspend fun updateProductSelected(userId: String, productId: String, isSelected: Boolean)

    //장바구니 상품 전체 활성화/비활성화
    @Query("UPDATE cart_products SET isSelected = :isAllSelected WHERE userId = :userId")
    suspend fun updateAllProductSelected(userId: String, isAllSelected: Boolean)

}