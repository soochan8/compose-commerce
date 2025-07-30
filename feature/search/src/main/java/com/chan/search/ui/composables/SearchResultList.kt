package com.chan.search.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.chan.android.ui.theme.MainColor
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.appTypography
import com.chan.search.ui.model.SearchResultModel

@Composable
fun SearchResultList(
    results: List<SearchResultModel>,
    searchQuery: String,
    modifier: Modifier = Modifier,
    onSearchResultItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = Spacing.spacing4)
    ) {
        items(
            items = results,
            key = { it.productId }
        ) { product ->
            HighlightedText(
                fullName = product.productName,
                searchText = searchQuery,
                modifier = Modifier.clickable {
                    onSearchResultItemClick(product.productName)
                }
            )
        }
    }
}

@Composable
private fun HighlightedText(
    fullName: String,
    searchText: String,
    modifier: Modifier = Modifier
) {
    val annotatedString = buildAnnotatedString {
        val startIndex = fullName.indexOf(searchText, ignoreCase = true)
        if (startIndex == -1) {
            append(fullName)
            return@buildAnnotatedString
        }

        val endIndex = startIndex + searchText.length

        if (startIndex > 0) {
            append(fullName.substring(0, startIndex))
        }

        withStyle(
            style = SpanStyle(
                color = MainColor,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(fullName.substring(startIndex, endIndex))
        }

        if (endIndex < fullName.length) {
            append(fullName.substring(endIndex))
        }
    }
    Text(
        text = annotatedString,
        style = MaterialTheme.appTypography.searchResultText,
        maxLines = 1,
        minLines = 1,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing4)
    )
} 