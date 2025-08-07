package com.chan.search.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.chan.android.ui.theme.Radius
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.appTypography
import com.chan.search.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecommendedKeywordList(
    recommendedKeywords: List<String>
) {
    Column(modifier = Modifier.padding(Spacing.spacing4)) {
        Text(
            text = stringResource(R.string.recommend_keyword),
            style = MaterialTheme.appTypography.searchTitle
        )
        Spacer(modifier = Modifier.height(Spacing.spacing2))
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Spacing.spacing2),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
        ) {
            recommendedKeywords.forEach { keyword ->
                RecommendedKeywordChip(keyword = keyword)
            }
        }
    }
}

@Composable
fun RecommendedKeywordChip(
    keyword: String
) {
    Surface(
        modifier = Modifier.padding(vertical = Spacing.spacing1),
        shape = RoundedCornerShape(Radius.radius6),
        color = Color.LightGray.copy(alpha = 0.2f)
    ) {
        Text(
            text = keyword,
            style = MaterialTheme.appTypography.searchChip,
            modifier = Modifier.padding(horizontal = Spacing.spacing4, vertical = Spacing.spacing2)
        )
    }
} 