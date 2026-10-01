package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AppTab
import com.example.ui.MainViewModel
import com.example.ui.components.*
import com.example.ui.screens.FindUsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MenuScreen
import com.example.ui.screens.ReservationsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.Zinc950

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                FarajaRestaurantApp()
            }
        }
    }
}

@Composable
fun FarajaRestaurantApp(
    viewModel: MainViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Back handler to navigate back to Home if on a sub-screen
    if (uiState.currentTab != AppTab.HOME) {
        BackHandler {
            viewModel.selectTab(AppTab.HOME)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Zinc950),
        containerColor = Zinc950,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            LoungeHeader(
                cartCount = uiState.cartItemCount,
                onOpenCart = { viewModel.toggleCartSheet(true) }
            )
        },
        bottomBar = {
            LoungeBottomNav(
                currentTab = uiState.currentTab,
                onTabSelected = { viewModel.selectTab(it) },
                cartCount = uiState.cartItemCount
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(
                targetState = uiState.currentTab,
                label = "screen_transition"
            ) { tab ->
                when (tab) {
                    AppTab.HOME -> {
                        HomeScreen(
                            onNavigateToTab = { viewModel.selectTab(it) },
                            onSelectItem = { viewModel.showItemDetail(it) },
                            onQuickAddToCart = { viewModel.addToCart(it) }
                        )
                    }
                    AppTab.MENU -> {
                        MenuScreen(
                            uiState = uiState,
                            filteredLocalDishes = viewModel.getFilteredLocalDishes(),
                            onSelectCategory = { viewModel.selectCategory(it) },
                            onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                            onSelectItem = { viewModel.showItemDetail(it) },
                            onQuickAddToCart = { viewModel.addToCart(it) },
                            onOpenCart = { viewModel.toggleCartSheet(true) },
                            onSaveDish = { viewModel.saveDishToDatabase(it) },
                            onDeleteDish = { viewModel.deleteDishFromDatabase(it) },
                            onNavigateToReservations = { _ -> viewModel.selectTab(AppTab.RESERVATIONS) }
                        )
                    }
                    AppTab.RESERVATIONS -> {
                        ReservationsScreen(
                            uiState = uiState,
                            onCreateReservation = { name, phone, guests, date, time, area, occ, notes ->
                                viewModel.createReservation(name, phone, guests, date, time, area, occ, notes)
                            },
                            onDismissSuccessDialog = { viewModel.dismissReservationDialog() }
                        )
                    }
                    AppTab.FIND_US -> {
                        FindUsScreen()
                    }
                }
            }
        }
    }

    // Cart Bottom Sheet
    if (uiState.showCartSheet) {
        CartBottomSheet(
            uiState = uiState,
            onDismiss = { viewModel.toggleCartSheet(false) },
            onUpdateQuantity = { item, delta -> viewModel.updateCartItemQuantity(item, delta) },
            onClearCart = { viewModel.clearCart() },
            onSetTipPercentage = { viewModel.setTipPercentage(it) },
            onSendWhatsAppOrder = {
                val orderMsg = viewModel.buildWhatsAppOrderMessage()
                launchWhatsApp(context, orderMsg)
            }
        )
    }

    // Dish & Cocktail Detail Dialog
    uiState.selectedMenuItemForDetail?.let { item ->
        ItemDetailDialog(
            item = item,
            isFavorite = uiState.favoriteItemIds.contains(item.id),
            onToggleFavorite = { viewModel.toggleFavorite(item.id) },
            onDismiss = { viewModel.showItemDetail(null) },
            onAddToCart = { qty, side, notes ->
                viewModel.addToCart(item, qty, side, notes)
            }
        )
    }
}
