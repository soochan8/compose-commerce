package com.chan.product.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.chan.android.ui.theme.Black
import com.chan.android.ui.theme.DefaultTagColor
import com.chan.android.ui.theme.FontSize
import com.chan.android.ui.theme.FontSize.productDetailReview
import com.chan.android.ui.theme.LightGray
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.TagBoxColor
import com.chan.android.ui.theme.TodayDeliveryTagColor
import com.chan.android.ui.theme.White
import com.chan.android.ui.theme.appTypography
import com.chan.product.ui.model.ProductDetailModel
import kotlinx.coroutines.launch

@Composable
fun ProductDetailHeader(productInfo: ProductDetailModel.ProductInfoModel) {
    Column {
        ProductImages(imageUrls = productInfo.images)

        ProductInfo(productInfo = productInfo)
        HorizontalDivider(
            thickness = 0.5.dp,
            color = LightGray.copy(alpha = 0.5f),
        )
    }
}


@Composable
fun ProductImages(
    imageUrls: List<String>,
    modifier: Modifier = Modifier
) {
    val pageCount = imageUrls.size
    if (pageCount == 0) return

    val scope = rememberCoroutineScope()

    val initialPage = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { Int.MAX_VALUE }
    )

    val arrowIconSize = 25.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
    ) {
        HorizontalPager(
            state = pagerState
        ) { pageIndex ->
            val index = (pageIndex - initialPage).mod(pageCount)

            AsyncImage(
                model = imageUrls[index],
                contentDescription = "상품 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentSize()
                .padding(bottom = Spacing.spacing2)
                .background(
                    color = Black.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(Spacing.spacing3)
                )
        ) {
            val currentPage = (pagerState.currentPage - initialPage).mod(pageCount)

            IconButton(
                modifier = Modifier
                    .size(arrowIconSize),
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "이전 이미지",
                    tint = Color.White,
                    modifier = Modifier.padding(horizontal = Spacing.spacing1)
                )
            }

            Text(
                text = "${currentPage + 1} / $pageCount",
                style = MaterialTheme.appTypography.tagLabel.copy(color = White),
                modifier = Modifier.padding(
                    vertical = Spacing.spacing1,
                    horizontal = Spacing.spacing1
                )
            )

            IconButton(
                modifier = Modifier
                    .size(arrowIconSize),
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "다음 이미지",
                    tint = Color.White,
                    modifier = Modifier.padding(horizontal = Spacing.spacing1)
                )
            }
        }
    }
}

@Composable
fun ProductInfo(productInfo: ProductDetailModel.ProductInfoModel) {
    Column(
        modifier = Modifier.padding(
            horizontal = Spacing.spacing4,
            vertical = Spacing.spacing5
        )
    ) {
        Text(
            text = productInfo.brandInfo.name,
            style = MaterialTheme.appTypography.productDetailBrandName
        )
        Spacer(modifier = Modifier.height(Spacing.spacing4))
        Text(
            text = productInfo.name,
            style = MaterialTheme.appTypography.productDetailName,
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(Spacing.spacing4))
        ProductPrice(price = productInfo.price)

        Spacer(modifier = Modifier.height(Spacing.spacing2))
        if (productInfo.tags.isNotEmpty()) {
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.spacing1)) {
                productInfo.tags.forEach { tag ->
                    ProductTags(tag)
                }
            }
        }

        Spacer(modifier = Modifier.height(Spacing.spacing5))
        ProductReviews(reviewSummary = productInfo.reviewSummary)
    }
}

@Composable
fun ProductPrice(price: ProductDetailModel.ProductInfoModel.PriceModel) {
    Text(
        text = price.originalPrice,
        style = MaterialTheme.appTypography.originPrice.copy(
            fontSize = FontSize.productDetailOriginPrice
        )
    )
    Row {
        Text(
            text = price.discountPercent,
            style = MaterialTheme.appTypography.discountPercent.copy(
                fontSize = FontSize.productDetailDiscount
            )
        )
        Spacer(modifier = Modifier.width(Spacing.spacing1))
        Text(
            text = price.discountPriceLabel,
            style = MaterialTheme.appTypography.discountPrice.copy(
                fontSize = FontSize.productDetailDiscount
            )
        )
    }
}

@Composable
fun ProductTags(tagLabel: String) {
    val tagColor = when (tagLabel) {
        "오늘드림" -> TodayDeliveryTagColor
        else -> DefaultTagColor
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(TagBoxColor)
            .padding(Spacing.spacing1)
    ) {
        Text(
            text = tagLabel,
            style = MaterialTheme.appTypography.tagLabel.copy(
                color = tagColor
            ),
            maxLines = 1,
            minLines = 1
        )
    }
}

@Composable
fun ProductReviews(reviewSummary: ProductDetailModel.ProductInfoModel.ReviewSummaryModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "star",
            tint = Black,
            modifier = Modifier.size(10.dp)
        )
        Spacer(modifier = Modifier.width(Spacing.spacing1))
        Text(
            text = reviewSummary.rating,
            style = MaterialTheme.appTypography.review.copy(
                color = Black,
                fontSize = productDetailReview
            )
        )
        Text(
            text = "|",
            fontSize = 9.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(horizontal = Spacing.spacing1)
        )
        Text(
            text = reviewSummary.count,
            style = MaterialTheme.appTypography.review.copy(
                color = Black,
                fontSize = productDetailReview
            )
        )
    }
}