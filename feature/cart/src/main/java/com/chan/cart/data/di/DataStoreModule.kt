package com.chan.cart.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.chan.database.datastore.cartProtoDataStore
import com.chan.cart.proto.Cart
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideCartDataStore(@ApplicationContext context: Context): DataStore<Cart> {
        return context.cartProtoDataStore
    }
}
