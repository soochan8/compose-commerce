package com.chan.database.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore

val Context.cartProtoDataStore: DataStore<com.chan.cart.proto.Cart> by dataStore(
    fileName = "cart.pb",
    serializer = CartSerializer
)
