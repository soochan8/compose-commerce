package com.chan.product.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.chan.product.ui.ProductDetailContract
import com.chan.product.ui.ProductDetailViewModel

@Composable
fun ProductDetailScreen(
    productId: String,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {

    val state by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setEvent(ProductDetailContract.Event.ProductDetailLoad)
    }

    Scaffold(
        bottomBar = {
            state.productDetailInfo?.let { productDetail ->
                ProductPurchaseBottomBar(productInfo = productDetail.productInfo)
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
                ProductDetailContent()
            }
        }
    }
}