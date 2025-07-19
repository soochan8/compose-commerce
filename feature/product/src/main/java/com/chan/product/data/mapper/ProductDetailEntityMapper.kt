package com.chan.product.data.mapper

import com.chan.database.entity.ProductDetailEntity
import com.chan.database.entity.ProductDetailEntity.DeliveryOptionsEntity
import com.chan.database.entity.ProductDetailEntity.ProductInfoEntity
import com.chan.database.entity.ProductDetailEntity.PurchaseOptionEntity
import com.chan.product.domain.vo.ProductDetailVO

fun ProductDetailEntity.toDomain(): ProductDetailVO {
    return ProductDetailVO(
        productId = productId,
        productInfo = productInfo.toDomain(),
        deliveryOptions = deliveryOptions.toDomain(),
        purchaseOptions = purchaseOptions.map { it.toDomain() }
    )
}

fun ProductInfoEntity.toDomain(): ProductDetailVO.ProductInfoVO {
    return ProductDetailVO.ProductInfoVO(
        name = name,
        images = images,
        brandInfo = brandInfo.toDomain(),
        price = price.toDomain(),
        tags = tags,
        reviewSummary = reviewSummary.toDomain()
    )
}

fun ProductInfoEntity.BrandInfoEntity.toDomain(): ProductDetailVO.ProductInfoVO.BrandInfoVO {
    return ProductDetailVO.ProductInfoVO.BrandInfoVO(
        id = id,
        name = name,
        logoImageUrl = logoImageUrl
    )
}

fun ProductInfoEntity.PriceEntity.toDomain(): ProductDetailVO.ProductInfoVO.PriceVO {
    return ProductDetailVO.ProductInfoVO.PriceVO(
        originalPrice = originalPrice,
        discountPercent = discountPercent,
        discountPrice = discountPrice
    )
}

fun ProductInfoEntity.ReviewSummaryEntity.toDomain(): ProductDetailVO.ProductInfoVO.ReviewSummaryVO {
    return ProductDetailVO.ProductInfoVO.ReviewSummaryVO(
        rating = rating,
        count = count,
        scorePercent = scorePercent
    )
}

fun DeliveryOptionsEntity.toDomain(): ProductDetailVO.DeliveryOptionsVO {
    return ProductDetailVO.DeliveryOptionsVO(
        standard = standard?.toDomain(),
        todayDelivery = todayDelivery?.toDomain(),
        pickup = pickup?.toDomain()
    )
}

fun DeliveryOptionsEntity.DeliveryDetailEntity.toDomain(): ProductDetailVO.DeliveryOptionsVO.DeliveryDetailVO {
    return ProductDetailVO.DeliveryOptionsVO.DeliveryDetailVO(
        isAvailable = isAvailable,
        fee = fee,
        description = description
    )
}

fun PurchaseOptionEntity.toDomain(): ProductDetailVO.PurchaseOptionVO {
    return ProductDetailVO.PurchaseOptionVO(
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