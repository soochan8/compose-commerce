package com.chan.cart.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.chan.cart.proto.Cart

val Context.cartProtoDataStore: DataStore<Cart> by dataStore(
    fileName = "cart.pb",
    serializer = CartSerializer
)
