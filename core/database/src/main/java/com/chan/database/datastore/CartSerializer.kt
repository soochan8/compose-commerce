package com.chan.database.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.chan.cart.proto.Cart
import java.io.InputStream
import java.io.OutputStream

object CartSerializer : Serializer<Cart> {
    override val defaultValue: Cart =
        Cart.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Cart {
        try {
            return Cart.parseFrom(input)
        } catch (exception: com.google.protobuf.InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Cart, output: OutputStream) = t.writeTo(output)
}
