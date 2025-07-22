package com.chan.login.data.di

import com.chan.login.data.repository.LoginRepositoryImpl
import com.chan.login.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.chan.login.data.repository.KakaoLoginManagerImpl
import com.chan.login.domain.KakaoLoginManager

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository

    @Singleton
    @Binds
    abstract fun bindKakaoLoginManager(
        kakaoLoginManagerImpl: KakaoLoginManagerImpl
    ): KakaoLoginManager
} 