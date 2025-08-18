package com.chan.search.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chan.android.ui.theme.LightGray
import com.chan.android.ui.theme.Radius
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.appTypography
import com.chan.search.R
import com.chan.search.ui.model.SearchHistoryModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecentSearchList(
    recentSearches: List<SearchHistoryModel>,
    onRemoveSearch: (String) -> Unit,
    onClearAllRecentSearches: () -> Unit,
    onSearchClick: (String) -> Unit,
) {
    Column(modifier = Modifier.padding(Spacing.spacing4)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.recent_search),
                style = MaterialTheme.appTypography.searchTitle
            )
            Text(
                text = stringResource(R.string.search_all_delete),
                style = MaterialTheme.appTypography.searchAllDelete,
                modifier = Modifier.clickable {
                    onClearAllRecentSearches()
                }
            )
        }
        Spacer(modifier = Modifier.height(Spacing.spacing4))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
        ) {
            recentSearches.forEach { search ->
                RecentSearchChip(
                    keyword = search.search,
                    onRemove = { onRemoveSearch(search.search) },
                    onSearchClick = { onSearchClick(it) }
                )
            }
        }
    }
}

@Composable
fun RecentSearchChip(
    keyword: String,
    onRemove: () -> Unit,
    onSearchClick: (String) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(Radius.radius6),
        color = Color.White,
        border = BorderStroke(1.dp, LightGray.copy(alpha = 0.2f)),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = Spacing.spacing3, vertical = Spacing.spacing2).clickable {
                    onSearchClick(keyword)
                }
        ) {
            Text(text = keyword, style = MaterialTheme.appTypography.searchChip)
            IconButton(
                onClick = onRemove,
                modifier = Modifier
                    .padding(start = Spacing.spacing1)
                    .size(Spacing.spacing4)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove recent search",
                    tint = Color.Gray
                )
            }
        }
    }
} 