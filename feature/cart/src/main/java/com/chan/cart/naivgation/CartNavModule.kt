package com.chan.cart.naivgation

import com.chan.navigation.NavGraphProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class CartNavModule {
    @Binds
    @IntoSet
    abstract fun bindCartNavGraph(graph: CartNavGraph): NavGraphProvider
}