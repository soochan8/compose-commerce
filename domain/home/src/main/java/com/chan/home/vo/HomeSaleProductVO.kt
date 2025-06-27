package com.chan.home.vo

import com.chan.home.vo.common.PriceVO


data class HomeSaleProductVO(
    val id: Int,
    val imageUrl: String,
    val productName: String,
    val price: PriceVO,
    val tags: List<String>?
)