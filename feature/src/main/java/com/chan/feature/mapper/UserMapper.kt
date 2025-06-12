package com.chan.feature.mapper

import com.chan.feature.domain.vo.UserVO
import com.chan.feature.ui.test.UserModel

fun UserVO.toPresentation(): UserModel {
    return UserModel(
        userName = name
    )
}