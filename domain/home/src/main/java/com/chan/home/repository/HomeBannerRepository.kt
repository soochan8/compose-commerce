package com.chan.home.repository

import com.chan.home.vo.HomeBannerVO

interface HomeBannerRepository {
    suspend fun getBanners() : List<HomeBannerVO>
}