package com.chan.home.domain.usecase

import com.chan.home.domain.repository.RankingRepository
import javax.inject.Inject

data class RankingUseCase (
    val rankingProductsUseCase: RankingProductsUseCase
)

class RankingProductsUseCase @Inject constructor(
    private val rankingRepository: RankingRepository
) {
    operator fun invoke() = rankingRepository.getAllProducts()
}