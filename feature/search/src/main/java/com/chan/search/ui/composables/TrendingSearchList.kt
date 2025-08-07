package com.chan.search.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.appTypography
import com.chan.search.R
import com.chan.search.domain.model.RankChange
import com.chan.search.ui.model.TrendingSearchModel

@Composable
fun TrendingSearchList(
    trendingSearches: List<TrendingSearchModel>,
    currentTime: String
) {
    Column(modifier = Modifier.padding(Spacing.spacing4)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.trending_search),
                style = MaterialTheme.appTypography.searchTitle
            )
            Text(
                text = "$currentTime 기준",
                style = MaterialTheme.appTypography.currentTime
            )
        }
        Spacer(modifier = Modifier.height(Spacing.spacing4))

        val leftColumn = trendingSearches.take(5)
        val rightColumn = trendingSearches.drop(5)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                leftColumn.forEach { item ->
                    TrendingSearchItem(item = item)
                }
            }
            Spacer(modifier = Modifier.width(Spacing.spacing4))
            Column(modifier = Modifier.weight(1f)) {
                rightColumn.forEach { item ->
                    TrendingSearchItem(item = item)
                }
            }
        }
    }
}

@Composable
fun TrendingSearchItem(
    item: TrendingSearchModel
) {
    Row(
        modifier = Modifier.padding(vertical = Spacing.spacing2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${item.rank}",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(Spacing.spacing6)
        )
        Text(
            text = item.keyword,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.appTypography.trendingSearch
        )
        RankChangeIndicator(change = item.change)
    }
}

@Composable
fun RankChangeIndicator(change: RankChange) {
    Box(
        modifier = Modifier
            .width(Spacing.spacing6)
            .height(Spacing.spacing5),
        contentAlignment = Alignment.Center
    ) {
        when (change) {
            RankChange.UP -> Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Rank Up",
                tint = Color.Red
            )

            RankChange.DOWN -> Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Rank Down",
                tint = Color.Blue
            )

            RankChange.NEW -> Text(
                text = stringResource(R.string.trending_new),
                style = MaterialTheme.appTypography.trendingRankNew
            )

            RankChange.NONE -> Text(text = "-", color = Color.Gray)
        }
    }
} 