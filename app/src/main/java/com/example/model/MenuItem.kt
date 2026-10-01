package com.example.model

enum class MenuCategory(val displayName: String) {
    ALL("All Picks"),
    NYAMA_CHOMA("Nyama Choma"),
    WET_DRY_FRY("Wet & Dry Fry"),
    SIDES("Sides & Greens"),
    COCKTAILS("Cocktails"),
    BEERS_CIDERS("Beers & Ciders"),
    WHISKEY_SPIRITS("Whiskey & Spirits"),
    NON_ALCOHOLIC("Mocktails & Soft")
}

data class MenuItem(
    val id: String,
    val name: String,
    val description: String,
    val priceKes: Int,
    val category: MenuCategory,
    val tag: String? = null,
    val isPopular: Boolean = false,
    val portionInfo: String = "Per Portion / Platter",
    val drawableRes: Int? = null,
    val rating: Double = 4.9
)
