package com.chan.cart.ui.popup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.chan.android.ui.theme.Radius
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.dividerColor
import com.chan.cart.CartContract
import com.chan.cart.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartPopupScreen(
    state: CartContract.State,
    onEvent: (CartContract.Event) -> Unit,
    onDismiss: () -> Unit
) {
    val oliveYoungGreen = Color(0xFF95C11F)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(Radius.radius4),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.spacing2),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.spacing4, vertical = Spacing.spacing1),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.select_option),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "닫기")
                    }
                }

                HorizontalDivider(color = dividerColor, thickness = 1.dp)

                //상품 정보
                Column(modifier = Modifier.padding(horizontal = Spacing.spacing5)) {
                    Row(
                        modifier = Modifier.padding(top = Spacing.spacing3),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = state.popupProductInfo.imageUrl,
                            contentDescription = state.popupProductInfo.productName,
                            modifier = Modifier
                                .size(72.dp)
                                .clip(RoundedCornerShape(Radius.radius2)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(Spacing.spacing4))
                        Text(
                            text = state.popupProductInfo.productName,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            VerticalDivider(modifier = Modifier.height(Spacing.spacing2))

            //하단 버튼
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.spacing5, vertical = Spacing.spacing3),
                horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2)
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(Radius.radius2),
                    border = BorderStroke(1.dp, oliveYoungGreen)
                ) {
                    Text(text = stringResource(R.string.close), color = oliveYoungGreen)
                }
                Button(
                    onClick = {
                        onEvent(CartContract.Event.AddToProduct(state.popupProductInfo.productId))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(Radius.radius2),
                    colors = ButtonDefaults.buttonColors(containerColor = oliveYoungGreen)
                ) {
                    Text(text = stringResource(R.string.cart_in))
                }
            }
        }
    }
}