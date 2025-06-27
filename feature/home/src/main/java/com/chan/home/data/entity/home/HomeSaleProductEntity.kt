package com.chan.home.data.entity.home

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chan.home.data.entity.PriceEntity
import com.chan.home.domain.vo.HomeSaleProductVO
import com.chan.home.domain.vo.common.PriceVO

@Entity(tableName = "homeSaleProduct")
data class HomeSaleProductEntity(
    @PrimaryKey
    val id: Int,
    val imageUrl: String,
    val productName: String,
    @Embedded val price: PriceEntity,
    val tags: List<String>? = null
)

fun HomeSaleProductEntity.toDomain(): HomeSaleProductVO {
    return HomeSaleProductVO(
        id = id,
        imageUrl = imageUrl,
        productName = productName,
        price = price.toDomain(),
        tags = tags
    )
}

fun PriceEntity.toDomain(): PriceVO {
    return PriceVO(
        discountPercent = discountPercent,
        discountedPrice = discountedPrice,
        originalPrice = originalPrice
    )
}
