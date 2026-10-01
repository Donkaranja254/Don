package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.TruceRepository
import com.example.model.LoungePhoto
import com.example.model.MenuItem
import com.example.model.PhotoCategory
import com.example.model.SeatingArea
import com.example.ui.theme.*

@Composable
fun AtmospherePhotoGrid(
    onSelectMenuItem: (MenuItem) -> Unit,
    onNavigateToReservations: (SeatingArea?) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf(PhotoCategory.ALL) }
    var activeLightboxPhoto by remember { mutableStateOf<LoungePhoto?>(null) }

    val filteredPhotos = remember(selectedCategory) {
        if (selectedCategory == PhotoCategory.ALL) {
            TruceRepository.loungePhotos
        } else {
            TruceRepository.loungePhotos.filter { it.category == selectedCategory }
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        // Category Filter Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(PhotoCategory.values()) { category ->
                val isSelected = selectedCategory == category
                Surface(
                    onClick = { selectedCategory = category },
                    color = if (isSelected) Amber500 else Zinc900,
                    shape = RoundedCornerShape(20.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSelected) Amber500 else Zinc800
                    ),
                    modifier = Modifier.testTag("gallery_chip_${category.name}")
                ) {
                    Text(
                        text = category.label,
                        color = if (isSelected) Zinc950 else Zinc300,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Grid Content
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false)
                .testTag("atmosphere_photo_grid")
        ) {
            items(filteredPhotos, key = { it.id }) { photo ->
                PhotoGridCard(
                    photo = photo,
                    onClick = { activeLightboxPhoto = photo }
                )
            }
        }
    }

    // Lightbox Full-view Modal Dialog
    activeLightboxPhoto?.let { photo ->
        PhotoLightboxDialog(
            photo = photo,
            onDismiss = { activeLightboxPhoto = null },
            onActionClick = {
                activeLightboxPhoto = null
                if (photo.linkedMenuItemId != null) {
                    val menuItem = TruceRepository.menuItems.find { it.id == photo.linkedMenuItemId }
                    if (menuItem != null) {
                        onSelectMenuItem(menuItem)
                    }
                } else if (photo.linkedSeatingArea != null) {
                    onNavigateToReservations(photo.linkedSeatingArea)
                }
            }
        )
    }
}

@Composable
fun PhotoGridCard(
    photo: LoungePhoto,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Zinc900),
        border = androidx.compose.foundation.BorderStroke(1.dp, Zinc800),
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .testTag("photo_card_${photo.id}")
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = photo.drawableRes),
                contentDescription = photo.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradient scrim for contrast
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Zinc950.copy(alpha = 0.4f),
                                Zinc950.copy(alpha = 0.95f)
                            )
                        )
                    )
            )

            // Tag at top-left
            Surface(
                color = Zinc950.copy(alpha = 0.75f),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Amber500.copy(alpha = 0.5f)),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
            ) {
                Text(
                    text = photo.tag,
                    color = Amber400,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                )
            }

            // Expand icon at top-right
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Zinc950.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ZoomIn,
                    contentDescription = "Expand",
                    tint = Zinc100,
                    modifier = Modifier.size(16.dp)
                )
            }

            // Text Info at bottom
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = photo.title,
                    color = Zinc100,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = photo.subtitle,
                    color = Zinc400,
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 1.dp)
                )
            }
        }
    }
}

@Composable
fun PhotoLightboxDialog(
    photo: LoungePhoto,
    onDismiss: () -> Unit,
    onActionClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Zinc900),
            border = androidx.compose.foundation.BorderStroke(1.dp, Zinc800),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .wrapContentHeight()
                .testTag("photo_lightbox_dialog")
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Large Photo Display
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                ) {
                    Image(
                        painter = painterResource(id = photo.drawableRes),
                        contentDescription = photo.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Close button
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Zinc950.copy(alpha = 0.75f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Zinc100,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Category Pill
                    Surface(
                        color = Amber500,
                        shape = RoundedCornerShape(topEnd = 10.dp),
                        modifier = Modifier.align(Alignment.BottomStart)
                    ) {
                        Text(
                            text = photo.tag,
                            color = Zinc950,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }

                // Description and Action
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Text(
                        text = photo.title,
                        color = Zinc100,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = photo.subtitle,
                        color = Amber400,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
                    )

                    Text(
                        text = photo.description,
                        color = Zinc300,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    val actionLabel = when {
                        photo.linkedMenuItemId != null -> "Order & Customize"
                        photo.linkedSeatingArea != null -> "Reserve this Seating Area"
                        else -> "Explore in Menu"
                    }

                    val actionIcon = when {
                        photo.linkedMenuItemId != null -> Icons.Default.RestaurantMenu
                        photo.linkedSeatingArea != null -> Icons.Default.EventSeat
                        else -> Icons.Default.Explore
                    }

                    AmberGradientButton(
                        text = actionLabel,
                        leadingIcon = actionIcon,
                        onClick = onActionClick,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
