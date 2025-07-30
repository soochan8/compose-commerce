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
        color = Black,
    ),
    val discountPercent: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = FontSize.discountPercent,
        color = Red
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
    ),


    //상품 상세
    val productDetailBrandName: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.productDetailBrandName,
        color = Black,
        lineHeight = LineHeight.productTitleSpacing
    ),

    val productDetailName: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.productDetailName,
        color = Black,
        lineHeight = LineHeight.productTitleSpacing
    ),

    val bottomBarTodayDelivery: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.bottomBarTodayDelivery,
        color = Black
    ),

    val bottomBarButton: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = FontSize.bottomBarButton
    ),

    val purchaseQuantity: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.purchaseQuantity,
        color = Black
    ),

    val purchasePriceLabel: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = FontSize.purchasePriceLabel,
        color = Black
    ),

    val purchaseQuantityLabel: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.purchaseQuantityLabel,
        color = Black
    ),

    val purchaseTotalPrice: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = FontSize.purchaseTotalPrice,
        color = Black
    ),

    //Search
    val searchHintTextStyle: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.searchHint,
        color = Gray
    ),

    val searchTitle: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = FontSize.searchTitle,
        color = Black,
        lineHeight = LineHeight.productTitleSpacing
    ),

    val searchAllDelete: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.searchAllDelete,
        color = LightGray,
        lineHeight = LineHeight.productTitleSpacing
    ),

    val currentTime: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.currentTime,
        color = LightGray,
    ),

    val searchChip: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.searchChipText,
        color = Black,
        lineHeight = LineHeight.productTitleSpacing
    ),

    val searchResultText: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.searchResultText,
        color = Black,
    ),
    val trendingSearch: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.trendingSearch,
        color = Black,
    ),
    val trendingRankNew: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.trendingRankNew,
        color = Gray,
    )

)