package com.chan.database.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.chan.cart.proto.Cart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Singleton
import kotlin.collections.getOrPut

@Singleton
object CartDataStoreManager {

    private val storeCache = ConcurrentHashMap<String, DataStore<Cart>>()
    private val scopeCache = ConcurrentHashMap<String, CoroutineScope>()

    fun getDataStore(context: Context, userId: String): DataStore<Cart> {
        return storeCache.getOrPut(userId) {
            val userScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
            scopeCache[userId] = userScope

            DataStoreFactory.create(
                serializer = CartSerializer,
                produceFile = { context.dataStoreFile("cart_$userId.pb") },
                scope = userScope
            )
        }
    }

    fun clearAll() {
        scopeCache.values.forEach { it.cancel() }
        scopeCache.clear()
        storeCache.clear()
    }
}