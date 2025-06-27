package com.chan.home.domain.vo

import com.chan.home.domain.vo.common.PriceVO


data class HomeSaleProductVO(
    val id: Int,
    val imageUrl: String,
    val productName: String,
    val price: PriceVO,
    val tags: List<String>?
)