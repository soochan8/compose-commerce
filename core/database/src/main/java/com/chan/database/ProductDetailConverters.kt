package com.chan.database

import androidx.room.TypeConverter
import com.chan.database.entity.ProductDetailEntity.DeliveryOptionsEntity
import com.chan.database.entity.ProductDetailEntity.ProductInfoEntity
import com.chan.database.entity.ProductDetailEntity.PurchaseOptionEntity
import com.google.gson.Gson

class ProductDetailConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromProductInfoEntity(value: ProductInfoEntity?): String? = gson.toJson(value)

    @TypeConverter
    fun toProductInfoEntity(value: String?): ProductInfoEntity? =
        gson.fromJson(value, ProductInfoEntity::class.java)

    @TypeConverter
    fun fromDeliveryOptionsEntity(value: DeliveryOptionsEntity?): String? = gson.toJson(value)

    @TypeConverter
    fun toDeliveryOptionsEntity(value: String?): DeliveryOptionsEntity? =
        gson.fromJson(value, DeliveryOptionsEntity::class.java)

    @TypeConverter
    fun fromPurchaseOptionList(value: List<PurchaseOptionEntity>?): String? = gson.toJson(value)

    @TypeConverter
    fun toPurchaseOptionList(value: String?): List<PurchaseOptionEntity>? =
        gson.fromJson(value, Array<PurchaseOptionEntity>::class.java)?.toList()
}