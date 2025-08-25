package com.chan.login.data.repository

import com.chan.database.dao.UserDao
import com.chan.login.data.mapper.toUserEntity
import com.chan.login.domain.repository.LoginRepository
import com.chan.login.domain.vo.UserVO
import com.chan.login.util.SecurityUtils
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
) : LoginRepository {
    override suspend fun registerUser(
        userId: String,
        userPw: String
    ): Result<Unit> {
        return runCatching {
            if (userDao.findByUsername(userId) != null) {
                throw SecurityException("이미 존재하는 아이디입니다.")
            }

            val salt = SecurityUtils.generateSalt()
            val hashedPassword = SecurityUtils.hashPassword(
                password = userPw.toCharArray(),
                salt = salt
            )
            val newUser = UserVO(
                userId = userId,
                hashedPassword = hashedPassword,
                salt = salt
            )

            userDao.insertUser(newUser.toUserEntity())
        }

    }

    override suspend fun appLogin(userId: String, userPw: String): Result<Unit> {
        return runCatching {
            val storedUser =
                userDao.findByUsername(userId) ?: throw SecurityException("존재하지 않는 아이디입니다.")

            val isPasswordCorrect = SecurityUtils.verifyPassword(
                password = userPw.toCharArray(),
                salt = storedUser.salt,
                hashedPassword = storedUser.hashedPassword
            )

            if (!isPasswordCorrect) throw SecurityException("비밀번호가 일치하지 않습니다.")

            storedUser
        }
    }
}