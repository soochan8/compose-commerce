package com.chan.product.ui.model

data class ProductDetailModel(
    val productId: String,
    val productInfo: ProductInfoModel,
    val deliveryOptions: DeliveryOptionsModel,
    val purchaseOptions: List<PurchaseOptionModel>
) {
    data class ProductInfoModel(
        val name: String,
        val images: List<String>,
        val brandInfo: BrandInfoModel,
        val price: PriceModel,
        val tags: List<String>,
        val reviewSummary: ReviewSummaryModel
    ) {
        data class BrandInfoModel(
            val id: String,
            val name: String,
            val logoImageUrl: String?
        )

        data class PriceModel(
            val originalPrice: Int,
            val discountPercent: Int,
            val discountPrice: Int
        )

        data class ReviewSummaryModel(
            val rating: Float,
            val count: Int,
            val scorePercent: Map<String, Int>
        )
    }

    data class DeliveryOptionsModel(
        val standard: DeliveryDetailModel?,
        val todayDelivery: DeliveryDetailModel?,
        val pickup: DeliveryDetailModel?
    ) {
        data class DeliveryDetailModel(
            val isAvailable: Boolean,
            val fee: Int,
            val freeShippingThreshold: Int? = null,
            val estimatedDeliveryDays: Int? = null,
            val description: String,
            val unavailableReason: String? = null
        )

    }

    data class PurchaseOptionModel(
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