package com.chan.product.data.di

import com.chan.product.data.repository.ProductDetailRepositoryImpl
import com.chan.product.domain.repository.ProductDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductDetailRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductDetailRepository(
        productDetailRepositoryImpl: ProductDetailRepositoryImpl
    ): ProductDetailRepository
}