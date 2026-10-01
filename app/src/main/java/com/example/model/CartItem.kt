package com.example.model

data class CartItem(
    val menuItem: MenuItem,
    val quantity: Int = 1,
    val sideChoice: String = "Ugali & Kachumbari",
    val notes: String = ""
) {
    val totalPriceKes: Int
        get() = menuItem.priceKes * quantity
}
