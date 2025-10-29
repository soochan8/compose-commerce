package com.chan.search.ui.composables.result.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import com.chan.android.model.ProductsModel
import com.chan.android.ui.CommonProductsCard
import com.chan.android.ui.theme.Spacing

fun LazyListScope.productGrid(
    products: List<ProductsModel>,
    onProductClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onCartClick: (String) -> Unit
) {
    val chunkedProducts = products.chunked(2)
    items(
        items = chunkedProducts,
        key = { row -> row.joinToString { it.productId } }
    ) { productRow ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2)
        ) {
            productRow.forEach { product ->
                Box(modifier = Modifier.weight(1f)) {
                    CommonProductsCard(
                        product = product,
                        onClick = { onProductClick(product.productId) },
                        onLikeClick = { onLikeClick(it) },
                        onCartClick = { onCartClick(it) }
                    )
                }
            }
            if (productRow.size == 1) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}