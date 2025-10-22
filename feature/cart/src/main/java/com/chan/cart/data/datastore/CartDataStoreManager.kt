package com.chan.cart.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.chan.cart.proto.Cart
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Singleton
import kotlin.collections.getOrPut

@Singleton
object CartDataStoreManager {

    private val storeCache = ConcurrentHashMap<String, DataStore<Cart>>()

    fun getDataStore(context: Context, userId: String): DataStore<Cart> {
        return storeCache.getOrPut(userId) {
            DataStoreFactory.create(
                serializer = CartSerializer,
                produceFile = { context.dataStoreFile("cart_$userId.pb") }
            )
        }
    }

    fun clearAll() {
        storeCache.clear()
    }
}