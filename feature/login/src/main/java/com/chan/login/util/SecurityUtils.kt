package com.chan.login.util

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object SecurityUtils {

    private const val ALGORITHM = "PBKDF2WithHmacSHA256"
    private const val ITERATIONS = 100000
    private const val KEY_LENGTH = 256
    private const val SALT_SIZE = 16

    //새로운 솔트 생성 (사용자별)
    fun generateSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(SALT_SIZE)
        random.nextBytes(salt)
        return salt
    }

    //비밀번호와 솔트를 사용하여 SHA-256 해시 생성
    fun hashPassword(password: CharArray, salt: ByteArray): String {
        val spec = PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH)
        val factory = SecretKeyFactory.getInstance(ALGORITHM)
        val hashedBytes = factory.generateSecret(spec).encoded
        return Base64.encodeToString(hashedBytes, Base64.NO_WRAP)
    }

    //로그인 시, 입력된 비밀번호가 저장된 해시와 일치하는 지 확인
    fun verifyPassword(password: CharArray, salt: ByteArray, hashedPassword: String): Boolean {
        val spec = PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH)
        val factory = SecretKeyFactory.getInstance(ALGORITHM)
        val testHashedBytes = factory.generateSecret(spec).encoded
        val hashedBytes = Base64.decode(hashedPassword, Base64.NO_WRAP)
        return hashedBytes.contentEquals(testHashedBytes)
    }
}