package com.chan.search.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import com.chan.android.ui.theme.Black
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.appTypography
import com.chan.search.R

@Composable
fun SearchTextField(
    search: String,
    onSearchChanged: (String) -> Unit,
    onClearSearch: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = search,
        onValueChange = onSearchChanged,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        singleLine = true,
        cursorBrush = SolidColor(Black),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .background(
                        color = Color.LightGray.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(Spacing.spacing4)
                    )
                    .padding(start = Spacing.spacing4)
                    .padding(vertical = Spacing.spacing3),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField()
                    if (search.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search_hint),
                            style = MaterialTheme.appTypography.searchHintTextStyle,
                        )
                    }
                }
                if (search.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear search",
                        modifier = Modifier
                            .clickable(onClick = onClearSearch)
                            .padding(horizontal = Spacing.spacing2)
                            .size(Spacing.spacing5)
                    )
                }
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier
                        .clickable(onClick = onSearchClick)
                        .padding(end = Spacing.spacing2)
                        .size(Spacing.spacing5)
                )
            }
        }
    )
} 