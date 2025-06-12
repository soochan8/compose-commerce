package com.chan.feature.data.mapper

import com.chan.feature.data.dto.TestUserDto
import com.chan.feature.domain.vo.UserVO

fun TestUserDto.toDomain() : UserVO {
    return UserVO(
        id = id,
        name = name
    )
}