package com.chan.home.mapper

import com.chan.home.domain.vo.HomeBannerVO
import com.chan.home.model.HomeBannerModel

fun HomeBannerVO.toPresentation(): HomeBannerModel {
    return HomeBannerModel(
        id = id,
        imageUrl = imageUrl,
        title = title
    )
}