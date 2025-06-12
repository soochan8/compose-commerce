package com.chan.feature.domain.usecase

import com.chan.feature.domain.repository.TestRepository
import com.chan.feature.domain.vo.UserVO
import javax.inject.Inject

class TestUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend fun invoke(): UserVO {
        return testRepository.getUser()
    }
}