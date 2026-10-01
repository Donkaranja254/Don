package com.example.model

enum class PhotoCategory(val label: String) {
    ALL("All Visuals"),
    FOOD_GRILL("Food & Grill"),
    COCKTAILS_BAR("Cocktails & Bar"),
    ATMOSPHERE_LOUNGE("Atmosphere & Pergola")
}

data class LoungePhoto(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val category: PhotoCategory,
    val tag: String,
    val drawableRes: Int,
    val linkedMenuItemId: String? = null,
    val linkedSeatingArea: SeatingArea? = null
)
