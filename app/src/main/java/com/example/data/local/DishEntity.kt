package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.MenuCategory
import com.example.model.MenuItem

@Entity(tableName = "dishes")
data class DishEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val priceKes: Int,
    val category: String,
    val tag: String? = null,
    val isPopular: Boolean = false,
    val portionInfo: String = "Per Portion / Platter",
    val drawableRes: Int? = null,
    val rating: Double = 4.9,
    val isAvailable: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun toMenuItem(): MenuItem {
        val cat = try {
            MenuCategory.valueOf(category)
        } catch (e: Exception) {
            MenuCategory.NYAMA_CHOMA
        }
        return MenuItem(
            id = id,
            name = name,
            description = description,
            priceKes = priceKes,
            category = cat,
            tag = tag,
            isPopular = isPopular,
            portionInfo = portionInfo,
            drawableRes = drawableRes,
            rating = rating
        )
    }
}
