package com.chan.home.domain.repository

import com.chan.home.domain.vo.HomeBannerVO

interface HomeBannerRepository {
    suspend fun getBanners() : List<HomeBannerVO>
}