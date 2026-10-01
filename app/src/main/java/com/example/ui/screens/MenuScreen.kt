package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.DishEntity
import com.example.model.MenuCategory
import com.example.model.MenuItem
import com.example.model.SeatingArea
import com.example.ui.UiState
import com.example.ui.components.AtmospherePhotoGrid
import com.example.ui.components.LocalDatabaseMenuComponent
import com.example.ui.theme.*

enum class MenuViewMode {
    MENU_ITEMS,
    PHOTO_GRID
}

@Composable
fun MenuScreen(
    uiState: UiState,
    filteredLocalDishes: List<DishEntity>,
    onSelectCategory: (MenuCategory) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSelectItem: (MenuItem) -> Unit,
    onQuickAddToCart: (MenuItem) -> Unit,
    onOpenCart: () -> Unit,
    onSaveDish: (DishEntity) -> Unit = {},
    onDeleteDish: (String) -> Unit = {},
    onNavigateToReservations: (SeatingArea?) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var currentViewMode by remember { mutableStateOf(MenuViewMode.MENU_ITEMS) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = if (uiState.cartItems.isNotEmpty()) 140.dp else 80.dp)
        ) {
            // Screen Title
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "FARAJA RESTAURANT & KITCHEN",
                    color = Amber500,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = if (currentViewMode == MenuViewMode.MENU_ITEMS) "Local Database Restaurant Menu" else "Atmosphere & Food Visuals",
                    color = Zinc100,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Segmented Toggle between Menu Items and Photo Grid
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Zinc900)
                        .border(1.dp, Zinc800, RoundedCornerShape(12.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Menu Items Tab
                    Surface(
                        onClick = { currentViewMode = MenuViewMode.MENU_ITEMS },
                        color = if (currentViewMode == MenuViewMode.MENU_ITEMS) Amber500 else Zinc900,
                        shape = RoundedCornerShape(9.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("toggle_menu_items")
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.RestaurantMenu,
                                contentDescription = null,
                                tint = if (currentViewMode == MenuViewMode.MENU_ITEMS) Zinc950 else Zinc300,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Dishes (${filteredLocalDishes.size})",
                                color = if (currentViewMode == MenuViewMode.MENU_ITEMS) Zinc950 else Zinc300,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }

                    // Photo Grid Tab
                    Surface(
                        onClick = { currentViewMode = MenuViewMode.PHOTO_GRID },
                        color = if (currentViewMode == MenuViewMode.PHOTO_GRID) Amber500 else Zinc900,
                        shape = RoundedCornerShape(9.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("toggle_photo_grid")
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoLibrary,
                                contentDescription = null,
                                tint = if (currentViewMode == MenuViewMode.PHOTO_GRID) Zinc950 else Zinc300,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Photo Grid",
                                color = if (currentViewMode == MenuViewMode.PHOTO_GRID) Zinc950 else Zinc300,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = if (currentViewMode == MenuViewMode.PHOTO_GRID) Zinc950 else Amber500,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "GALLERY",
                                    color = if (currentViewMode == MenuViewMode.PHOTO_GRID) Amber400 else Zinc950,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                    }
                }
            }

            // View Mode Content
            if (currentViewMode == MenuViewMode.PHOTO_GRID) {
                // PHOTO GRID COMPONENT
                AtmospherePhotoGrid(
                    onSelectMenuItem = onSelectItem,
                    onNavigateToReservations = onNavigateToReservations,
                    modifier = Modifier.weight(1f)
                )
            } else {
                // LOCAL DATABASE MENU COMPONENT (Stored in Room SQLite)
                LocalDatabaseMenuComponent(
                    dishes = filteredLocalDishes,
                    onAddToCart = onQuickAddToCart,
                    onSelectItemDetail = onSelectItem,
                    onSaveDish = onSaveDish,
                    onDeleteDish = onDeleteDish,
                    selectedCategory = uiState.selectedCategory,
                    onSelectCategory = onSelectCategory,
                    searchQuery = uiState.searchQuery,
                    onSearchQueryChange = onSearchQueryChange,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Floating Tab Bar
        if (uiState.cartItems.isNotEmpty()) {
            Surface(
                color = Zinc900,
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Amber500.copy(alpha = 0.5f)),
                shadowElevation = 8.dp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp, vertical = 80.dp)
                    .fillMaxWidth()
                    .testTag("floating_cart_bar")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Amber500),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = uiState.cartItemCount.toString(),
                                color = Zinc950,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Current Table Tab",
                                color = Zinc100,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "KES ${"%,d".format(uiState.cartSubtotalKes)}",
                                color = Amber400,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 13.sp
                            )
                        }
                    }

                    Button(
                        onClick = onOpenCart,
                        colors = ButtonDefaults.buttonColors(containerColor = Amber500),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "View Tab",
                            color = Zinc950,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.ReceiptLong,
                            contentDescription = null,
                            tint = Zinc950,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
