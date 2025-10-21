package com.chan.cart.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.chan.cart.proto.Cart
import javax.inject.Singleton

@Singleton
object CartDataStoreManager {

    private val storeCache = mutableMapOf<String, DataStore<Cart>>()

    fun getDataStore(context: Context, userId: String): DataStore<Cart> {
        return storeCache.getOrPut(userId) {
            DataStoreFactory.create(
                serializer = CartSerializer,
                produceFile = { context.dataStoreFile("cart_$userId.pb") }
            )
        }
    }
}