package com.chan.product.navigation

import com.chan.navigation.NavGraphProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductDetailNavModule {

    @Binds
    @IntoSet
    abstract fun bindProductDetailNavGraph(graph: ProductDetailNavGraph) : NavGraphProvider
}