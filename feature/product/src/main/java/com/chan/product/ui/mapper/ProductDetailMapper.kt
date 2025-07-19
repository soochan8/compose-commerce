package com.chan.product.ui.mapper

import com.chan.product.domain.vo.ProductDetailVO
import com.chan.product.domain.vo.ProductDetailVO.DeliveryOptionsVO
import com.chan.product.domain.vo.ProductDetailVO.ProductInfoVO
import com.chan.product.ui.model.ProductDetailModel

fun ProductDetailVO.toProductDetailModel(): ProductDetailModel {
    return ProductDetailModel(
        productId = productId,
        productInfo = productInfo.toPresentationModel(),
        deliveryOptions = deliveryOptions.toPresentationModel(),
        purchaseOptions = purchaseOptions.map { it.toPresentationModel() }
    )
}

fun ProductInfoVO.toPresentationModel(): ProductDetailModel.ProductInfoModel {
    return ProductDetailModel.ProductInfoModel(
        name = name,
        images = images,
        brandInfo = brandInfo.toPresentationModel(),
        price = price.toPresentationModel(),
        tags = tags,
        reviewSummary = reviewSummary.toPresentationModel()
    )
}

fun ProductInfoVO.BrandInfoVO.toPresentationModel(): ProductDetailModel.ProductInfoModel.BrandInfoModel {
    return ProductDetailModel.ProductInfoModel.BrandInfoModel(
        id = id,
        name = name,
        logoImageUrl = logoImageUrl
    )
}

fun ProductInfoVO.PriceVO.toPresentationModel(): ProductDetailModel.ProductInfoModel.PriceModel {
    return ProductDetailModel.ProductInfoModel.PriceModel(
        originalPrice = originalPrice,
        discountPercent = discountPercent,
        discountPrice = discountPrice
    )
}

fun ProductInfoVO.ReviewSummaryVO.toPresentationModel(): ProductDetailModel.ProductInfoModel.ReviewSummaryModel {
    return ProductDetailModel.ProductInfoModel.ReviewSummaryModel(
        rating = rating,
        count = count,
        scorePercent = scorePercent
    )
}

fun DeliveryOptionsVO.toPresentationModel(): ProductDetailModel.DeliveryOptionsModel {
    return ProductDetailModel.DeliveryOptionsModel(
        standard = standard?.toPresentationModel(),
        todayDelivery = todayDelivery?.toPresentationModel(),
        pickup = pickup?.toPresentationModel()
    )
}

fun DeliveryOptionsVO.DeliveryDetailVO.toPresentationModel(): ProductDetailModel.DeliveryOptionsModel.DeliveryDetailModel {
    return ProductDetailModel.DeliveryOptionsModel.DeliveryDetailModel(
        isAvailable = isAvailable,
        fee = fee,
        description = description
    )
}

fun ProductDetailVO.PurchaseOptionVO.toPresentationModel(): ProductDetailModel.PurchaseOptionModel {
    return ProductDetailModel.PurchaseOptionModel(
        optionId = optionId,
        productId = productId,
        image = image,
        optionName = optionName,
        discountPercent = discountPercent,
        discountPrice = discountPrice,
        tags = tags,
        soldOut = soldOut,
        stock = stock
    )
}