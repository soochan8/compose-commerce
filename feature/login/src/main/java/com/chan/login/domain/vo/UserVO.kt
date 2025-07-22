package com.chan.login.domain.vo

data class UserVO(
    val id: Int = 0,
    val userId: String,
    val hashedPassword: String,
    val salt: ByteArray
)