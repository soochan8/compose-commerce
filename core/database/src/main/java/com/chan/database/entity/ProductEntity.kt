package com.chan.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.chan.database.Converters

@Entity(tableName = "product")
@TypeConverters(Converters::class)
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val categories: List<Categories>
) {
    data class Categories(
        val id: String,
        val name: String,
        val subCategories: List<SubCategories>
    ) {
        data class SubCategories(
            val categoryId: String,
            val categoryName: String,
            val products: List<Products>
        ) {
            data class Products(
                val productId: String,
                val productName: String,
                val brandName: String,
                val imageUrl: String,
                val originalPrice: Int,
                val discountPercent: Int,
                val discountPrice: Int,
                val tags: List<String>,
                val reviewRating: Float,
                val reviewCount: Int
            )
        }
    }
}
