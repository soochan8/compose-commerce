package com.chan.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chan.database.dto.CategoryDetailTabsDto
import com.chan.database.dto.CategoryTabDto
import com.chan.database.dto.FilterCategoriesDto
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
    suspend fun getSaleProducts(limit: Int): List<ProductEntity.Categories.SubCategories.Products> {
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
            ?.map {
                CategoryDetailTabsDto(
                    categoryId = it.categoryId,
                    categoryName = it.categoryName
                )
            }
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

    // 선택된 하위 카테고리 이름 목록으로 상품 필터링하기
    suspend fun getProductsBySubCategoryNames(subCategoryNames: Set<String>): List<ProductEntity.Categories.SubCategories.Products> {
        if (subCategoryNames.isEmpty()) {
            return emptyList() // 선택된 필터가 없으면 빈 리스트 반환
        }
        return getAll()
            .flatMap { it.categories }
            .flatMap { it.subCategories }
            .filter { subCategory -> subCategoryNames.contains(subCategory.categoryName) }
            .flatMap { it.products }
            .distinctBy { it.productId } // 여러 카테고리에 속한 동일 상품 중복 제거
    }

    // 선택된 하위 카테고리 이름 목록으로 상품 개수 세기
    suspend fun getProductCountBySubCategoryNames(subCategoryNames: Set<String>): Int {
        val subCategoriesWithProducts = getAll()
            .flatMap { it.categories }
            .flatMap { it.subCategories }

        val products = if (subCategoryNames.isEmpty()) {
            // 선택된 필터가 없으면 전체 상품이 대상
            subCategoriesWithProducts.flatMap { it.products }
        } else {
            // 선택된 필터가 있으면 해당 카테고리의 상품만 대상
            subCategoriesWithProducts
                .filter { subCategory -> subCategoryNames.contains(subCategory.categoryName) }
                .flatMap { it.products }
        }
        // 중복 제거 후 개수 반환
        return products.distinctBy { it.productId }.size
    }

    //필터 카테고리 정보 가져오기
    suspend fun getFilterCategories(): List<FilterCategoriesDto> {
        return getAll()
            .flatMap { productEntity -> productEntity.categories }
            .distinctBy { category -> category.name }
            .map { category ->
                FilterCategoriesDto(
                    categoryId = category.id,
                    name = category.name,
                    subCategories = category.subCategories.map { subCategory ->
                        FilterCategoriesDto.SubCategoryDto(
                            subCategoryId = subCategory.categoryId,
                            subCategoryName = subCategory.categoryName
                        )
                    }
                )
            }
    }
}