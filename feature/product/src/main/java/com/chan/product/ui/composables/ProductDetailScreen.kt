package com.chan.product.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chan.product.ui.ProductDetailContract
import kotlinx.coroutines.flow.Flow

@Composable
fun ProductDetailScreen(
    productId: String,
    state: ProductDetailContract.State,
    onEvent: (ProductDetailContract.Event) -> Unit,
    effect: Flow<ProductDetailContract.Effect>
) {
    Scaffold(
        bottomBar = {
            state.productDetailInfo?.let { productDetail ->
                ProductPurchaseBottomBar(state, onEvent)
            }
        }
    ) { paddingValues ->
        state.productDetailInfo?.let { productDetail ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
            ) {
                ProductDetailHeader(productDetail.productInfo)
                ProductDetailContent(state, onEvent, effect)
            }
        }
    }
}