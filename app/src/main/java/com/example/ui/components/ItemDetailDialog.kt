package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.model.MenuCategory
import com.example.model.MenuItem
import com.example.ui.theme.*

@Composable
fun ItemDetailDialog(
    item: MenuItem,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onDismiss: () -> Unit,
    onAddToCart: (quantity: Int, sideChoice: String, notes: String) -> Unit
) {
    var quantity by remember { mutableStateOf(1) }
    var selectedSide by remember {
        mutableStateOf(
            if (item.category == MenuCategory.NYAMA_CHOMA || item.category == MenuCategory.WET_DRY_FRY)
                "Ugali & Kachumbari"
            else "Standard Serve"
        )
    }
    var specialNotes by remember { mutableStateOf("") }

    val sidesList = listOf(
        "Ugali & Kachumbari",
        "Masala Fries & Kachumbari",
        "Traditional Mukimo",
        "Plain Fries",
        "Kachumbari Only"
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Zinc900),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f)
                .border(1.dp, Zinc800, RoundedCornerShape(24.dp))
                .testTag("item_detail_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Header Image / Art
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                ) {
                    MealVisual(
                        dishId = item.id,
                        dishName = item.name,
                        category = item.category.name,
                        overrideDrawableRes = item.drawableRes,
                        showBadge = true,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Top Bar overlay with close and favorite
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Zinc950.copy(alpha = 0.7f))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Zinc100,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        IconButton(
                            onClick = onToggleFavorite,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Zinc950.copy(alpha = 0.7f))
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) GrillFlame else Zinc100,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Price Tag Badge
                    Surface(
                        color = Amber500,
                        shape = RoundedCornerShape(topStart = 12.dp),
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                    ) {
                        Text(
                            text = "KES ${"%,d".format(item.priceKes)}",
                            color = Zinc950,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }
                }

                // Details Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    if (item.tag != null) {
                        Surface(
                            color = Zinc800,
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.padding(bottom = 6.dp)
                        ) {
                            Text(
                                text = item.tag,
                                color = Amber400,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Text(
                        text = item.name,
                        color = Zinc100,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 6.dp)
                    ) {
                        RatingStars(rating = item.rating)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = item.portionInfo,
                            color = Zinc400,
                            fontSize = 12.sp
                        )
                    }

                    Text(
                        text = item.description,
                        color = Zinc300,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Side option if Choma or Fry
                    if (item.category == MenuCategory.NYAMA_CHOMA || item.category == MenuCategory.WET_DRY_FRY) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Choose Accompaniment / Side",
                            color = Zinc100,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            sidesList.forEach { side ->
                                val isSelected = selectedSide == side
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) Zinc800 else Zinc850)
                                        .border(
                                            1.dp,
                                            if (isSelected) Amber500 else Zinc800,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable { selectedSide = side }
                                        .padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = side,
                                        color = if (isSelected) Amber400 else Zinc300,
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { selectedSide = side },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = Amber500,
                                            unselectedColor = Zinc600
                                        )
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Special Notes Input
                    OutlinedTextField(
                        value = specialNotes,
                        onValueChange = { specialNotes = it },
                        label = { Text("Special Preparation / Notes (Optional)", color = Zinc400) },
                        placeholder = { Text("e.g. Extra spicy, well done, cold beer glass", color = Zinc600, fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Amber500,
                            unfocusedBorderColor = Zinc700,
                            focusedTextColor = Zinc100,
                            unfocusedTextColor = Zinc100
                        ),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Quantity and Add to Tab
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(Zinc800)
                                .padding(horizontal = 4.dp, vertical = 4.dp)
                        ) {
                            IconButton(
                                onClick = { if (quantity > 1) quantity-- },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Decrease",
                                    tint = Zinc200
                                )
                            }

                            Text(
                                text = quantity.toString(),
                                color = Zinc100,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )

                            IconButton(
                                onClick = { quantity++ },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Increase",
                                    tint = Amber400
                                )
                            }
                        }

                        val totalItemPrice = item.priceKes * quantity
                        AmberGradientButton(
                            text = "Add (KES ${"%,d".format(totalItemPrice)})",
                            leadingIcon = Icons.Default.ReceiptLong,
                            onClick = {
                                onAddToCart(quantity, selectedSide, specialNotes)
                                onDismiss()
                            },
                            modifier = Modifier.testTag("add_item_to_tab_button")
                        )
                    }
                }
            }
        }
    }
}
