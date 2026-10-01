package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.TruceRepository
import com.example.data.local.DishEntity
import com.example.data.local.DishRepository
import com.example.data.local.TruceDatabase
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

enum class AppTab(val label: String) {
    HOME("Home"),
    MENU("Menu & Grill"),
    RESERVATIONS("Reservations"),
    FIND_US("Find Us")
}

data class UiState(
    val currentTab: AppTab = AppTab.HOME,
    val selectedCategory: MenuCategory = MenuCategory.ALL,
    val searchQuery: String = "",
    val cartItems: List<CartItem> = emptyList(),
    val favoriteItemIds: Set<String> = setOf("pork_choma_platter", "long_island_tea"),
    val localDishes: List<DishEntity> = emptyList(),
    val reservations: List<Reservation> = listOf(
        Reservation(
            id = "res_sample",
            guestName = "VIP Lounge Guest",
            guestPhone = "+254 712 345678",
            guestsCount = 4,
            dateText = "Tonight",
            timeSlotText = "8:30 PM",
            seatingArea = SeatingArea.OUTDOOR_GARDEN,
            occasion = Occasion.CASUAL_CHOMA,
            specialNotes = "Table near garden terrace",
            confirmationCode = "FARAJA-7721",
            status = "Confirmed"
        )
    ),
    val lastConfirmedReservation: Reservation? = null,
    val showReservationSuccessDialog: Boolean = false,
    val showCartSheet: Boolean = false,
    val selectedMenuItemForDetail: MenuItem? = null,
    val tipPercentage: Int = 10 // 0, 5, 10, 15
) {
    val cartSubtotalKes: Int
        get() = cartItems.sumOf { it.totalPriceKes }

    val tipAmountKes: Int
        get() = (cartSubtotalKes * tipPercentage) / 100

    val cartTotalKes: Int
        get() = cartSubtotalKes + tipAmountKes

    val cartItemCount: Int
        get() = cartItems.sumOf { it.quantity }
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = TruceDatabase.getInstance(application)
    val dishRepository = DishRepository(database.dishDao())

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dishRepository.checkAndSeedIfEmpty()
        }
        viewModelScope.launch {
            dishRepository.allDishes.collect { list ->
                _uiState.update { it.copy(localDishes = list) }
            }
        }
    }

    fun selectTab(tab: AppTab) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun selectCategory(category: MenuCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun toggleFavorite(itemId: String) {
        _uiState.update { state ->
            val updated = state.favoriteItemIds.toMutableSet()
            if (updated.contains(itemId)) {
                updated.remove(itemId)
            } else {
                updated.add(itemId)
            }
            state.copy(favoriteItemIds = updated)
        }
    }

    fun addToCart(menuItem: MenuItem, quantity: Int = 1, sideChoice: String = "Ugali & Kachumbari", notes: String = "") {
        _uiState.update { state ->
            val existing = state.cartItems.find { it.menuItem.id == menuItem.id && it.sideChoice == sideChoice }
            val updated = if (existing != null) {
                state.cartItems.map {
                    if (it.menuItem.id == menuItem.id && it.sideChoice == sideChoice) {
                        it.copy(quantity = it.quantity + quantity)
                    } else it
                }
            } else {
                state.cartItems + CartItem(menuItem, quantity, sideChoice, notes)
            }
            state.copy(cartItems = updated)
        }
    }

    fun updateCartItemQuantity(menuItem: MenuItem, delta: Int) {
        _uiState.update { state ->
            val updated = state.cartItems.mapNotNull { item ->
                if (item.menuItem.id == menuItem.id) {
                    val newQty = item.quantity + delta
                    if (newQty > 0) item.copy(quantity = newQty) else null
                } else item
            }
            state.copy(cartItems = updated)
        }
    }

    fun clearCart() {
        _uiState.update { it.copy(cartItems = emptyList()) }
    }

    fun setTipPercentage(percent: Int) {
        _uiState.update { it.copy(tipPercentage = percent) }
    }

    fun toggleCartSheet(show: Boolean) {
        _uiState.update { it.copy(showCartSheet = show) }
    }

    fun showItemDetail(item: MenuItem?) {
        _uiState.update { it.copy(selectedMenuItemForDetail = item) }
    }

    fun saveDishToDatabase(dish: DishEntity) {
        viewModelScope.launch {
            dishRepository.addDish(dish)
        }
    }

    fun deleteDishFromDatabase(dishId: String) {
        viewModelScope.launch {
            dishRepository.deleteDish(dishId)
        }
    }

    fun createReservation(
        guestName: String,
        guestPhone: String,
        guestsCount: Int,
        dateText: String,
        timeSlotText: String,
        seatingArea: SeatingArea,
        occasion: Occasion,
        specialNotes: String
    ): Reservation {
        val randomNum = (1000..9999).random()
        val code = "FARAJA-$randomNum"
        val reservation = Reservation(
            id = UUID.randomUUID().toString(),
            guestName = guestName.ifBlank { "Guest" },
            guestPhone = guestPhone.ifBlank { "+254 700 000000" },
            guestsCount = guestsCount,
            dateText = dateText,
            timeSlotText = timeSlotText,
            seatingArea = seatingArea,
            occasion = occasion,
            specialNotes = specialNotes,
            confirmationCode = code,
            status = "Confirmed"
        )

        _uiState.update { state ->
            state.copy(
                reservations = listOf(reservation) + state.reservations,
                lastConfirmedReservation = reservation,
                showReservationSuccessDialog = true
            )
        }
        return reservation
    }

    fun dismissReservationDialog() {
        _uiState.update { it.copy(showReservationSuccessDialog = false) }
    }

    fun getFilteredLocalDishes(): List<DishEntity> {
        val state = _uiState.value
        val query = state.searchQuery.trim().lowercase()
        return state.localDishes.filter { dish ->
            val matchesCategory = (state.selectedCategory == MenuCategory.ALL) ||
                    dish.category.equals(state.selectedCategory.name, ignoreCase = true)
            val matchesQuery = query.isEmpty() ||
                    dish.name.lowercase().contains(query) ||
                    dish.description.lowercase().contains(query) ||
                    (dish.tag?.lowercase()?.contains(query) == true)
            matchesCategory && matchesQuery
        }
    }

    fun buildWhatsAppOrderMessage(): String {
        val state = _uiState.value
        val sb = StringBuilder()
        sb.append("🥩 *NEW ORDER - FARAJA RESTAURANT*\n")
        sb.append("📍 Ruiru Town, Kiambu (Next to National Bank)\n\n")
        sb.append("*Items Ordered:*\n")
        state.cartItems.forEachIndexed { i, it ->
            sb.append("${i + 1}. ${it.quantity}x ${it.menuItem.name} — KES ${it.totalPriceKes}\n")
            if (it.sideChoice.isNotBlank()) {
                sb.append("   • Side: ${it.sideChoice}\n")
            }
            if (it.notes.isNotBlank()) {
                sb.append("   • Note: ${it.notes}\n")
            }
        }
        sb.append("\n*Subtotal:* KES ${state.cartSubtotalKes}\n")
        if (state.tipAmountKes > 0) {
            sb.append("*Service / Tip (${state.tipPercentage}%):* KES ${state.tipAmountKes}\n")
        }
        sb.append("*Grand Total:* KES ${state.cartTotalKes}\n\n")
        sb.append("Please confirm our table order / pickup. Thank you!")
        return sb.toString()
    }

    fun buildWhatsAppReservationMessage(res: Reservation): String {
        return """
            🍸 *TABLE RESERVATION REQUEST*
            🏛️ *Faraja Restaurant (Ruiru Town)*
            
            • *Code:* ${res.confirmationCode}
            • *Guest Name:* ${res.guestName}
            • *Phone:* ${res.guestPhone}
            • *Guests:* ${res.guestsCount} people
            • *Date:* ${res.dateText}
            • *Time:* ${res.timeSlotText}
            • *Seating Area:* ${res.seatingArea.label}
            • *Occasion:* ${res.occasion.label}
            ${if (res.specialNotes.isNotBlank()) "• *Notes:* ${res.specialNotes}\n" else ""}
            
            Please confirm my reservation. Looking forward to good food & great drinks!
        """.trimIndent()
    }
}
