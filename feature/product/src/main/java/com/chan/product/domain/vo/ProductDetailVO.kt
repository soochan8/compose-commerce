package com.chan.product.domain.vo

data class ProductDetailVO(
    val productId: String,
    val productInfo: ProductInfoVO,
    val deliveryOptions: DeliveryOptionsVO,
    val purchaseOptions: List<PurchaseOptionVO>
) {
    data class ProductInfoVO(
        val name: String,
        val images: List<String>,
        val brandInfo: BrandInfoVO,
        val price: PriceVO,
        val tags: List<String>,
        val reviewSummary: ReviewSummaryVO
    ) {
        data class BrandInfoVO(
            val id: String,
            val name: String,
            val logoImageUrl: String?
        )

        data class PriceVO(
            val originalPrice: Int,
            val discountPercent: Int,
            val discountPrice: Int
        )

        data class ReviewSummaryVO(
            val rating: Float,
            val count: Int,
            val scorePercent: Map<String, Int>
        )
    }

    data class DeliveryOptionsVO(
        val standard: DeliveryDetailVO?,
        val todayDelivery: DeliveryDetailVO?,
        val pickup: DeliveryDetailVO?
    ) {
        data class DeliveryDetailVO(
            val isAvailable: Boolean,
            val fee: Int,
            val freeShippingThreshold: Int? = null,
            val estimatedDeliveryDays: Int? = null,
            val description: String,
            val unavailableReason: String? = null
        )

    }

    data class PurchaseOptionVO(
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








