package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TruceRepository
import com.example.ui.theme.*

@Composable
fun LoungeHeader(
    cartCount: Int,
    onOpenCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Surface(
        color = Zinc950.copy(alpha = 0.95f),
        tonalElevation = 6.dp,
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Zinc800.copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "FARAJA",
                        color = Amber500,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.2.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "RESTAURANT",
                        color = Zinc400,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Text(
                        text = "Ruiru Town",
                        color = Zinc400,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = " • ",
                        color = Zinc600,
                        fontSize = 11.sp
                    )
                    LiveOpenBadge()
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // Call Quick Action
                IconButton(
                    onClick = { launchDialer(context, TruceRepository.PHONE_NUMBER) },
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Zinc850)
                        .border(1.dp, Zinc700, CircleShape)
                        .testTag("header_call_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call Truce Lounge",
                        tint = Amber400,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Tab / Bill button with badge
                BadgedBox(
                    badge = {
                        if (cartCount > 0) {
                            Badge(
                                containerColor = Amber500,
                                contentColor = Zinc950
                            ) {
                                Text(
                                    text = cartCount.toString(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                ) {
                    IconButton(
                        onClick = onOpenCart,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Zinc850)
                            .border(1.dp, Zinc700, CircleShape)
                            .testTag("header_cart_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ReceiptLong,
                            contentDescription = "View Current Tab",
                            tint = if (cartCount > 0) Amber400 else Zinc300,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
