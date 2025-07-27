package com.chan.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chan.database.dto.CategoryDetailTabsDto
import com.chan.database.dto.CategoryTabDto
import com.chan.database.entity.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    suspend fun getAll(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    //인기 상품 가져오기
    suspend fun getPopularProducts(limit: Int): List<ProductEntity.Categories.SubCategories.Products> {
        return getAll()
            .flatMap { it.categories }
            .flatMap { it.subCategories }
            .flatMap { it.products }
            .shuffled()
            .take(limit)
    }

    //카테고리 랭킹
    @Query("SELECT id, name FROM product")
    suspend fun getCategoryTabs(): List<CategoryTabDto>

    @Query("SELECT * FROM product WHERE id = :categoryId")
    suspend fun getProductByCategoryId(categoryId: String): ProductEntity?
    suspend fun getProductsByCategoryId(categoryId: String): List<ProductEntity.Categories.SubCategories.Products> {
        return getProductByCategoryId(categoryId)
            ?.categories
            ?.flatMap { it.subCategories }
            ?.flatMap { it.products }
            ?.take(5)
            ?: emptyList()
    }

    //세일 상품 가져오기
    suspend fun getSaleProducts(limit : Int): List<ProductEntity.Categories.SubCategories.Products> {
        return getAll()
            .flatMap { it.categories }
            .flatMap { it.subCategories }
            .flatMap { it.products }
            .filter { it.discountPercent > 0 }
            .shuffled()
            .take(limit)
    }

    // 동일한 상위 카테고리에 속한 모든 하위 카테고리 정보(id, name) 가져오기
    suspend fun getSiblingSubCategories(subCategoryId: String): List<CategoryDetailTabsDto> {
        return getAll()
            .flatMap { it.categories }
            .firstOrNull { category ->
                category.subCategories.any { it.categoryId == subCategoryId }
            }
            ?.subCategories
            ?.map { CategoryDetailTabsDto(categoryId = it.categoryId, categoryName = it.categoryName) }
            ?: emptyList()
    }

    //해당 카테고리에 따른 리스트 상품 가져오기
    suspend fun getProductsBySubCategoryId(subCategoryId: String): List<ProductEntity.Categories.SubCategories.Products> {
        return getAll()
            .flatMap { it.categories }
            .flatMap { it.subCategories }
            .find { it.categoryId == subCategoryId }
            ?.products
            ?: emptyList()
    }

    // 상품 이름으로 검색하기
    suspend fun searchProductsByName(query: String): List<ProductEntity.Categories.SubCategories.Products> {
        if (query.isBlank()) {
            return emptyList()
        }
        return getAll()
            .flatMap { it.categories }
            .flatMap { it.subCategories }
            .flatMap { it.products }
            .filter { it.productName.contains(query, ignoreCase = true) }
    }
}