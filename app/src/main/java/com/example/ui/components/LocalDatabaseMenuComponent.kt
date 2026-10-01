package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.data.local.DishEntity
import com.example.model.MenuCategory
import com.example.model.MenuItem
import com.example.ui.theme.*
import java.util.UUID

@Composable
fun LocalDatabaseMenuComponent(
    dishes: List<DishEntity>,
    onAddToCart: (MenuItem) -> Unit,
    onSelectItemDetail: (MenuItem) -> Unit,
    onSaveDish: (DishEntity) -> Unit,
    onDeleteDish: (String) -> Unit,
    selectedCategory: MenuCategory,
    onSelectCategory: (MenuCategory) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddEditDialog by remember { mutableStateOf(false) }
    var editingDish by remember { mutableStateOf<DishEntity?>(null) }
    var dishToDelete by remember { mutableStateOf<DishEntity?>(null) }

    Column(modifier = modifier.fillMaxWidth()) {
        // Local DB Header Card with metadata & Add Action
        Surface(
            color = Zinc900,
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Zinc800),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .testTag("local_db_status_card")
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Amber500.copy(alpha = 0.15f))
                            .border(1.dp, Amber500.copy(alpha = 0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Storage,
                            contentDescription = null,
                            tint = Amber500,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Local Room Database",
                                color = Zinc100,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = EmeraldLive.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "OFFLINE SQLITE",
                                    color = EmeraldLive,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                        Text(
                            text = "${dishes.size} dishes loaded locally with prices & descriptions",
                            color = Zinc400,
                            fontSize = 11.sp
                        )
                    }
                }

                // Add Dish Button
                FilledTonalButton(
                    onClick = {
                        editingDish = null
                        showAddEditDialog = true
                    },
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Amber500,
                        contentColor = Zinc950
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.testTag("add_dish_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "New Dish", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = { Text("Search dishes in local database...", color = Zinc500, fontSize = 13.sp) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Amber500
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear",
                            tint = Zinc400
                        )
                    }
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Zinc900,
                unfocusedContainerColor = Zinc900,
                focusedBorderColor = Amber500,
                unfocusedBorderColor = Zinc800,
                focusedTextColor = Zinc100,
                unfocusedTextColor = Zinc100
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .testTag("db_menu_search_input")
        )

        // Category filter chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(MenuCategory.values()) { category ->
                val isSelected = selectedCategory == category
                Surface(
                    onClick = { onSelectCategory(category) },
                    color = if (isSelected) Amber500 else Zinc900,
                    shape = RoundedCornerShape(20.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSelected) Amber500 else Zinc800
                    ),
                    modifier = Modifier.testTag("db_category_chip_${category.name}")
                ) {
                    Text(
                        text = category.displayName,
                        color = if (isSelected) Zinc950 else Zinc300,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                    )
                }
            }
        }

        // Dishes List from Room
        if (dishes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.RestaurantMenu,
                        contentDescription = null,
                        tint = Zinc600,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "No dishes found in database",
                        color = Zinc300,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "Tap 'New Dish' above to insert a dish into SQLite",
                        color = Zinc500,
                        fontSize = 12.sp
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .testTag("dishes_list_view")
            ) {
                items(dishes, key = { it.id }) { dish ->
                    LocalDishCard(
                        dish = dish,
                        onClick = { onSelectItemDetail(dish.toMenuItem()) },
                        onAddToCart = { onAddToCart(dish.toMenuItem()) },
                        onEdit = {
                            editingDish = dish
                            showAddEditDialog = true
                        },
                        onDelete = { dishToDelete = dish }
                    )
                }
            }
        }
    }

    // Add or Edit Dish Dialog
    if (showAddEditDialog) {
        AddEditDishDialog(
            dishToEdit = editingDish,
            onDismiss = {
                showAddEditDialog = false
                editingDish = null
            },
            onSave = { dish ->
                onSaveDish(dish)
                showAddEditDialog = false
                editingDish = null
            }
        )
    }

    // Delete Confirmation Dialog
    dishToDelete?.let { dish ->
        AlertDialog(
            onDismissRequest = { dishToDelete = null },
            containerColor = Zinc900,
            title = {
                Text(
                    text = "Remove from Database?",
                    color = Zinc100,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete '${dish.name}' (KES ${"%,d".format(dish.priceKes)}) from the local restaurant menu database?",
                    color = Zinc300,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteDish(dish.id)
                        dishToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GrillFlame)
                ) {
                    Text("Delete Dish", color = PureWhite, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { dishToDelete = null }) {
                    Text("Cancel", color = Zinc400)
                }
            }
        )
    }
}

@Composable
fun LocalDishCard(
    dish: DishEntity,
    onClick: () -> Unit,
    onAddToCart: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = Zinc900,
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Zinc800),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("dish_card_${dish.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Dish Image / Distinct Meal Picture & Badge
                MealVisual(
                    dishId = dish.id,
                    dishName = dish.name,
                    category = dish.category,
                    overrideDrawableRes = dish.drawableRes,
                    showBadge = true,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Name, Category, Price
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = dish.name,
                            color = Zinc100,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            maxLines = 1,
                            modifier = Modifier.weight(1f)
                        )

                        Text(
                            text = "KES ${"%,d".format(dish.priceKes)}",
                            color = Amber400,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Surface(
                            color = Zinc800,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = dish.category.replace("_", " "),
                                color = Zinc300,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        if (dish.tag != null) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = Amber500.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = dish.tag,
                                    color = Amber400,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    // Description stored in database
                    Text(
                        text = dish.description,
                        color = Zinc400,
                        fontSize = 12.sp,
                        maxLines = 2,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Zinc800)
            Spacer(modifier = Modifier.height(8.dp))

            // Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dish.portionInfo,
                    color = Zinc500,
                    fontSize = 11.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Edit Dish
                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Zinc800)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Dish",
                            tint = Zinc300,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    // Delete Dish
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Zinc800)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Delete Dish",
                            tint = GrillFlame,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    // Add to Tab
                    Button(
                        onClick = onAddToCart,
                        colors = ButtonDefaults.buttonColors(containerColor = Amber500),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.defaultMinSize(minHeight = 32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Zinc950,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Add to Tab",
                            color = Zinc950,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddEditDishDialog(
    dishToEdit: DishEntity?,
    onDismiss: () -> Unit,
    onSave: (DishEntity) -> Unit
) {
    var name by remember { mutableStateOf(dishToEdit?.name ?: "") }
    var description by remember { mutableStateOf(dishToEdit?.description ?: "") }
    var priceText by remember { mutableStateOf(dishToEdit?.priceKes?.toString() ?: "") }
    var category by remember { mutableStateOf(dishToEdit?.category ?: MenuCategory.NYAMA_CHOMA.name) }
    var tag by remember { mutableStateOf(dishToEdit?.tag ?: "Chef's Special") }
    var portionInfo by remember { mutableStateOf(dishToEdit?.portionInfo ?: "1 kg Portion") }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Zinc900),
            border = androidx.compose.foundation.BorderStroke(1.dp, Zinc800),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .wrapContentHeight()
                .testTag("add_edit_dish_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (dishToEdit != null) Icons.Default.Edit else Icons.Default.AddCircle,
                        contentDescription = null,
                        tint = Amber500,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (dishToEdit != null) "Edit Dish Details" else "Add New Restaurant Dish",
                        color = Zinc100,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Dishes are saved persistently into the local Room database.",
                    color = Zinc400,
                    fontSize = 12.sp
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Dish Name", color = Zinc400) },
                    placeholder = { Text("e.g. Garlic Herb Roast Mbuzi", color = Zinc600) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Amber500,
                        unfocusedBorderColor = Zinc700,
                        focusedTextColor = Zinc100,
                        unfocusedTextColor = Zinc100
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = priceText,
                    onValueChange = { priceText = it },
                    label = { Text("Price (KES)", color = Zinc400) },
                    placeholder = { Text("e.g. 1850", color = Zinc600) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Amber500,
                        unfocusedBorderColor = Zinc700,
                        focusedTextColor = Zinc100,
                        unfocusedTextColor = Zinc100
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Dish Description", color = Zinc400) },
                    placeholder = { Text("Ingredients, taste profile, accompaniments...", color = Zinc600) },
                    minLines = 2,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Amber500,
                        unfocusedBorderColor = Zinc700,
                        focusedTextColor = Zinc100,
                        unfocusedTextColor = Zinc100
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = portionInfo,
                    onValueChange = { portionInfo = it },
                    label = { Text("Portion / Serving Info", color = Zinc400) },
                    placeholder = { Text("e.g. 1 kg platter (Serves 2-3)", color = Zinc600) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Amber500,
                        unfocusedBorderColor = Zinc700,
                        focusedTextColor = Zinc100,
                        unfocusedTextColor = Zinc100
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = GrillFlame,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel", color = Zinc300)
                    }

                    Button(
                        onClick = {
                            val price = priceText.toIntOrNull()
                            if (name.isBlank()) {
                                errorMessage = "Please enter a dish name"
                            } else if (price == null || price <= 0) {
                                errorMessage = "Please enter a valid price in KES"
                            } else if (description.isBlank()) {
                                errorMessage = "Please enter a description"
                            } else {
                                val entity = DishEntity(
                                    id = dishToEdit?.id ?: UUID.randomUUID().toString(),
                                    name = name.trim(),
                                    description = description.trim(),
                                    priceKes = price,
                                    category = category,
                                    tag = tag.ifBlank { null },
                                    isPopular = dishToEdit?.isPopular ?: false,
                                    portionInfo = portionInfo.ifBlank { "Standard Portion" },
                                    drawableRes = dishToEdit?.drawableRes ?: R.drawable.img_nyama_choma,
                                    rating = dishToEdit?.rating ?: 4.9,
                                    isAvailable = true
                                )
                                onSave(entity)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Amber500),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Save Dish", color = Zinc950, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
