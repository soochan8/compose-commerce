package com.chan.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.chan.database.ProductDetailConverters

@Entity(tableName = "productDetail")
@TypeConverters(ProductDetailConverters::class)
data class ProductDetailEntity(
    @PrimaryKey val productId: String,
    val productInfo: ProductInfoEntity,
    val deliveryOptions: DeliveryOptionsEntity,
    val purchaseOptions: List<PurchaseOptionEntity>
) {
    data class ProductInfoEntity(
        val name: String,
        val images: List<String>,
        val brandInfo: BrandInfoEntity,
        val price: PriceEntity,
        val tags: List<String>,
        val reviewSummary: ReviewSummaryEntity
    ) {
        data class BrandInfoEntity(
            val id: String,
            val name: String,
            val logoImageUrl: String?
        )

        data class PriceEntity(
            val originalPrice: Int,
            val discountPercent: Int,
            val discountPrice: Int
        )

        data class ReviewSummaryEntity(
            val rating: Float,
            val count: Int,
            val scorePercent: Map<String, Int>
        )
    }

    data class DeliveryOptionsEntity(
        val standard: DeliveryDetailEntity?,
        val todayDelivery: DeliveryDetailEntity?,
        val pickup: DeliveryDetailEntity?
    ) {
        data class DeliveryDetailEntity(
            val isAvailable: Boolean,
            val fee: Int,
            val freeShippingThreshold: Int? = null,
            val estimatedDeliveryDays: Int? = null,
            val description: String,
            val unavailableReason: String? = null
        )
    }

    data class PurchaseOptionEntity(
        val optionId: String,
        val productId: String,
        val image: String,
        val optionName: String,
        val discountPercent: Int,
        val discountPrice: Int,
        val tags: List<String>,
        val soldOut: Boolean,
        val stock: Int
    )
}
