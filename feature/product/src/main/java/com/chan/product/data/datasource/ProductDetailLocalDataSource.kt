package com.chan.product.data.datasource

import android.content.Context
import com.chan.database.entity.ProductDetailEntity
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import javax.inject.Inject

class ProductDetailLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun getProductDetail(productId: String): ProductDetailEntity? =
        withContext(Dispatchers.IO) {
            try {
                context.assets.open("product_detail.json").use { inputStream ->
                    InputStreamReader(inputStream).use { reader ->
                        val result = Gson().fromJson(reader, ProductDetailEntity::class.java)

                        if (result.productId == productId) result
                        else null
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
}