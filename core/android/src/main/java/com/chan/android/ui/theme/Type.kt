package com.chan.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

val LocalAppTypography = compositionLocalOf { AppTypography() }

val MaterialTheme.appTypography: AppTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalAppTypography.current

data class AppTypography(
    val appTitle: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = FontSize.appTitle,
        color = Black
    ),
    val title: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = FontSize.title,
        color = Black
    ),
    val productTitle: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = FontSize.productTitle,
        color = Black,
        lineHeight = LineHeight.productTitleSpacing
    ),
    val originPrice: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.originPrice,
        color = LightGray,
        textDecoration = TextDecoration.LineThrough
    ),
    val discountPrice: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = FontSize.discountPrice,
        color = Red,
    ),
    val discountPercent: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = FontSize.discountPercent,
        color = Black
    ),
    val tagLabel: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.tag,
    ),
    val review: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.review,
        color = Gray
    ),
    val loginTextFieldStyle: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.loginField,
        color = LightGray
    )
)