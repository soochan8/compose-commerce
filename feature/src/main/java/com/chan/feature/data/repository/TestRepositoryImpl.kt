package com.chan.feature.data.repository

import com.chan.feature.data.datasource.TestDataSource
import com.chan.feature.data.mapper.toDomain
import com.chan.feature.domain.repository.TestRepository
import com.chan.feature.domain.vo.UserVO
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val testDataSource: TestDataSource
) : TestRepository {
    override suspend fun getUser(): UserVO {
        return testDataSource.getUser().toDomain()
    }
}