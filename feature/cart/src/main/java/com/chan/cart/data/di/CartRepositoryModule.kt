package com.chan.cart.data.di

import com.chan.cart.data.CartRepositoryImpl
import com.chan.cart.domain.CartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CartRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCartRepository(
        categoryRepositoryImpl: CartRepositoryImpl
    ) : CartRepository
}