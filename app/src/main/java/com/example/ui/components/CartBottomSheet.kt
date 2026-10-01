package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CartItem
import com.example.model.MenuItem
import com.example.ui.UiState
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartBottomSheet(
    uiState: UiState,
    onDismiss: () -> Unit,
    onUpdateQuantity: (MenuItem, Int) -> Unit,
    onClearCart: () -> Unit,
    onSetTipPercentage: (Int) -> Unit,
    onSendWhatsAppOrder: () -> Unit
) {
    val context = LocalContext.current
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = modalBottomSheetState,
        containerColor = Zinc900,
        contentColor = Zinc100,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = Zinc600
            )
        },
        modifier = Modifier.testTag("cart_bottom_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ReceiptLong,
                        contentDescription = null,
                        tint = Amber500,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Your Table Tab",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Zinc100
                    )
                }

                if (uiState.cartItems.isNotEmpty()) {
                    TextButton(
                        onClick = onClearCart,
                        modifier = Modifier.testTag("clear_cart_button")
                    ) {
                        Text(
                            text = "Clear All",
                            color = GrillFlame,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Text(
                text = "Faraja Restaurant • Kitchen & Dining (Ruiru Town)",
                color = Zinc400,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (uiState.cartItems.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.RestaurantMenu,
                            contentDescription = null,
                            tint = Zinc600,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Your tab is currently empty",
                            color = Zinc300,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Explore our Nyama Choma, Platters & Cocktails to start building your order.",
                            color = Zinc400,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }
                }
            } else {
                // List of items
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(weight = 1f, fill = false),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.cartItems) { item ->
                        CartItemRow(
                            item = item,
                            onIncrement = { onUpdateQuantity(item.menuItem, 1) },
                            onDecrement = { onUpdateQuantity(item.menuItem, -1) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Zinc800)
                Spacer(modifier = Modifier.height(12.dp))

                // Tip / Staff Gratuity Selection
                Text(
                    text = "Staff Gratuity / Service Tip",
                    color = Zinc300,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(0, 5, 10, 15).forEach { percent ->
                        val isSelected = uiState.tipPercentage == percent
                        Surface(
                            onClick = { onSetTipPercentage(percent) },
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) Amber500 else Zinc800,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = if (percent == 0) "None" else "$percent%",
                                color = if (isSelected) Zinc950 else Zinc300,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Breakdown
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Zinc850)
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Subtotal", color = Zinc400, fontSize = 13.sp)
                        Text(
                            text = "KES ${"%,d".format(uiState.cartSubtotalKes)}",
                            color = Zinc200,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    if (uiState.tipAmountKes > 0) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Service Tip (${uiState.tipPercentage}%)",
                                color = Zinc400,
                                fontSize = 13.sp
                            )
                            Text(
                                text = "KES ${"%,d".format(uiState.tipAmountKes)}",
                                color = Amber400,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    HorizontalDivider(color = Zinc700, modifier = Modifier.padding(vertical = 4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total Amount",
                            color = Zinc100,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "KES ${"%,d".format(uiState.cartTotalKes)}",
                            color = Amber500,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // M-Pesa & Payment Notice
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Zinc800.copy(alpha = 0.5f))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Payment,
                        contentDescription = null,
                        tint = EmeraldLive,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Pay at table/counter via M-Pesa, Card or Cash",
                        color = Zinc400,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Submit to WhatsApp / Waiter
                AmberGradientButton(
                    text = "Send Order via WhatsApp",
                    leadingIcon = Icons.Default.Send,
                    onClick = onSendWhatsAppOrder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("send_whatsapp_order_button")
                )
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Zinc850)
            .border(1.dp, Zinc800, RoundedCornerShape(10.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.menuItem.name,
                color = Zinc100,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            if (item.sideChoice.isNotBlank()) {
                Text(
                    text = "Side: ${item.sideChoice}",
                    color = Amber400,
                    fontSize = 11.sp
                )
            }
            Text(
                text = "KES ${"%,d".format(item.totalPriceKes)}",
                color = Zinc300,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = onDecrement,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Zinc800)
            ) {
                Icon(
                    imageVector = if (item.quantity == 1) Icons.Default.Delete else Icons.Default.Remove,
                    contentDescription = "Decrease",
                    tint = if (item.quantity == 1) GrillFlame else Zinc200,
                    modifier = Modifier.size(16.dp)
                )
            }

            Text(
                text = item.quantity.toString(),
                color = Zinc100,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.widthIn(min = 20.dp),
                textAlign = TextAlign.Center
            )

            IconButton(
                onClick = onIncrement,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Amber500)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase",
                    tint = Zinc950,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
