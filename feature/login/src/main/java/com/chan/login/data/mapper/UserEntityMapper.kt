package com.chan.login.data.mapper

import com.chan.database.entity.UserEntity
import com.chan.login.domain.vo.UserVO

fun UserVO.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        userId = userId,
        hashedPassword = hashedPassword,
        salt = salt
    )
}

fun UserEntity.toUserVO(): UserVO {
    return UserVO(
        id = id,
        userId = userId,
        hashedPassword = hashedPassword,
        salt = salt
    )
}