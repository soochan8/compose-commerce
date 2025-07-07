package com.chan.database.entity.category

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.chan.database.dao.category.CategoryConverters

@Entity(tableName = "categoryTable")
@TypeConverters(CategoryConverters::class)
data class CategoryEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val subCategories: List<SubCategoryEntity>
) {
    data class SubCategoryEntity(
        val id: Int,
        val name: String,
        val itemGroups: List<SubCategoryItems>
    ) {
        data class SubCategoryItems(
            val id: Int,
            val name: String,
            val sortOrder: Int
        )
    }
}