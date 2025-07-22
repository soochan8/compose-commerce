package com.chan.login.data.repository

import com.chan.database.dao.UserDao
import com.chan.login.data.mapper.toUserEntity
import com.chan.login.data.mapper.toUserVO
import com.chan.login.domain.repository.LoginRepository
import com.chan.login.domain.vo.UserVO
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : LoginRepository {
    override suspend fun registerUser(user: UserVO) {
        userDao.insertUser(user.toUserEntity())
    }

    override suspend fun findUserByUserId(userId: String): UserVO? {
        val user = userDao.findByUsername(userId)

        return user?.toUserVO()
    }
}