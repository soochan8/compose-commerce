package com.chan.feature.data.datasource

import com.chan.feature.data.dto.TestUserDto
import javax.inject.Inject

//service
class TestDataSource @Inject constructor(){
    suspend fun getUser() : TestUserDto {
        return TestUserDto(
            id = "ididid",
            name = "soochan"
        )
    }
}