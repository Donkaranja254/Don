package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.MenuCategory
import com.example.ui.theme.*

data class MealVisualMeta(
    val drawableRes: Int,
    val icon: ImageVector,
    val badgeLabel: String,
    val accentColor: Color,
    val secondaryTint: Color
)

fun getMealVisualMeta(dishId: String, dishName: String, category: String): MealVisualMeta {
    val id = dishId.lowercase()
    val name = dishName.lowercase()

    return when {
        id.contains("pork_choma") || name.contains("pork choma") -> MealVisualMeta(
            drawableRes = R.drawable.img_nyama_choma,
            icon = Icons.Default.LocalFireDepartment,
            badgeLabel = "Pork Choma",
            accentColor = Color(0xFFEF4444),
            secondaryTint = Color(0xFF7F1D1D)
        )
        id.contains("mbuzi_choma") || name.contains("mbuzi choma") -> MealVisualMeta(
            drawableRes = R.drawable.img_nyama_choma,
            icon = Icons.Default.OutdoorGrill,
            badgeLabel = "Goat Ribs",
            accentColor = Color(0xFFF59E0B),
            secondaryTint = Color(0xFF78350F)
        )
        id.contains("kienyeji") || name.contains("chicken") || name.contains("kienyeji") -> MealVisualMeta(
            drawableRes = R.drawable.img_roast_chicken,
            icon = Icons.Default.Restaurant,
            badgeLabel = "Roast Chicken",
            accentColor = Color(0xFFFBBF24),
            secondaryTint = Color(0xFF854D0E)
        )
        id.contains("beef_choma") || id.contains("skewer") || name.contains("skewer") -> MealVisualMeta(
            drawableRes = R.drawable.img_nyama_choma,
            icon = Icons.Default.DinnerDining,
            badgeLabel = "Beef Skewers",
            accentColor = Color(0xFFE11D48),
            secondaryTint = Color(0xFF881337)
        )
        id.contains("carnivore") || name.contains("carnivore") || name.contains("feast") -> MealVisualMeta(
            drawableRes = R.drawable.img_nyama_choma,
            icon = Icons.Default.WorkspacePremium,
            badgeLabel = "Feast Board",
            accentColor = Color(0xFFFFD700),
            secondaryTint = Color(0xFF713F12)
        )
        id.contains("wet_fry_mbuzi") || name.contains("wet fry mbuzi") -> MealVisualMeta(
            drawableRes = R.drawable.img_wet_fry,
            icon = Icons.Default.SoupKitchen,
            badgeLabel = "Rich Stew",
            accentColor = Color(0xFFD97706),
            secondaryTint = Color(0xFF451A03)
        )
        id.contains("dry_fry") || name.contains("dry fry") -> MealVisualMeta(
            drawableRes = R.drawable.img_wet_fry,
            icon = Icons.Default.Whatshot,
            badgeLabel = "Sizzling Wok",
            accentColor = Color(0xFFEA580C),
            secondaryTint = Color(0xFF7C2D12)
        )
        id.contains("wings") || name.contains("wings") -> MealVisualMeta(
            drawableRes = R.drawable.img_roast_chicken,
            icon = Icons.Default.LocalFireDepartment,
            badgeLabel = "Glazed Wings",
            accentColor = Color(0xFFDC2626),
            secondaryTint = Color(0xFF991B1B)
        )
        id.contains("beef_wet") || name.contains("wet fry beef") -> MealVisualMeta(
            drawableRes = R.drawable.img_wet_fry,
            icon = Icons.Default.RamenDining,
            badgeLabel = "Beef Fillet",
            accentColor = Color(0xFFB45309),
            secondaryTint = Color(0xFF78350F)
        )
        id.contains("kachumbari") || name.contains("kachumbari") -> MealVisualMeta(
            drawableRes = R.drawable.img_sides_platter,
            icon = Icons.Default.Eco,
            badgeLabel = "Lime Salad",
            accentColor = Color(0xFF10B981),
            secondaryTint = Color(0xFF064E3B)
        )
        id.contains("mukimo") || name.contains("mukimo") -> MealVisualMeta(
            drawableRes = R.drawable.img_sides_platter,
            icon = Icons.Default.Grass,
            badgeLabel = "Greens Mukimo",
            accentColor = Color(0xFF84CC16),
            secondaryTint = Color(0xFF365314)
        )
        id.contains("ugali") || name.contains("ugali") -> MealVisualMeta(
            drawableRes = R.drawable.img_sides_platter,
            icon = Icons.Default.BakeryDining,
            badgeLabel = "Fluffy Ugali",
            accentColor = Color(0xFFF3F4F6),
            secondaryTint = Color(0xFF374151)
        )
        id.contains("fries") || name.contains("fries") -> MealVisualMeta(
            drawableRes = R.drawable.img_sides_platter,
            icon = Icons.Default.Fastfood,
            badgeLabel = "Masala Fries",
            accentColor = Color(0xFFF59E0B),
            secondaryTint = Color(0xFF78350F)
        )
        id.contains("long_island") || name.contains("long island") -> MealVisualMeta(
            drawableRes = R.drawable.img_cocktails,
            icon = Icons.Default.LocalBar,
            badgeLabel = "5-Spirit Mix",
            accentColor = Color(0xFF06B6D4),
            secondaryTint = Color(0xFF164E63)
        )
        id.contains("whiskey_sour") || name.contains("whiskey sour") -> MealVisualMeta(
            drawableRes = R.drawable.img_cocktails,
            icon = Icons.Default.WineBar,
            badgeLabel = "Citrus Sour",
            accentColor = Color(0xFFF59E0B),
            secondaryTint = Color(0xFF78350F)
        )
        id.contains("sunset") || name.contains("sunset") -> MealVisualMeta(
            drawableRes = R.drawable.img_cocktails,
            icon = Icons.Default.WbSunny,
            badgeLabel = "Sunset Rum",
            accentColor = Color(0xFFEC4899),
            secondaryTint = Color(0xFF831843)
        )
        id.contains("mule") || name.contains("mule") -> MealVisualMeta(
            drawableRes = R.drawable.img_cocktails,
            icon = Icons.Default.AcUnit,
            badgeLabel = "Copper Mule",
            accentColor = Color(0xFF14B8A6),
            secondaryTint = Color(0xFF134E4A)
        )
        id.contains("tusker") || name.contains("tusker") -> MealVisualMeta(
            drawableRes = R.drawable.img_chilled_beers,
            icon = Icons.Default.SportsBar,
            badgeLabel = "Cold Lager",
            accentColor = Color(0xFFEAB308),
            secondaryTint = Color(0xFF713F12)
        )
        id.contains("cider") || name.contains("cider") -> MealVisualMeta(
            drawableRes = R.drawable.img_chilled_beers,
            icon = Icons.Default.LocalDrink,
            badgeLabel = "Apple Cider",
            accentColor = Color(0xFFA3E635),
            secondaryTint = Color(0xFF3F6212)
        )
        id.contains("heineken") || name.contains("heineken") -> MealVisualMeta(
            drawableRes = R.drawable.img_chilled_beers,
            icon = Icons.Default.Star,
            badgeLabel = "Heineken Malt",
            accentColor = Color(0xFF22C55E),
            secondaryTint = Color(0xFF14532D)
        )
        id.contains("guinness") || name.contains("guinness") -> MealVisualMeta(
            drawableRes = R.drawable.img_chilled_beers,
            icon = Icons.Default.Nightlife,
            badgeLabel = "Foreign Stout",
            accentColor = Color(0xFFD97706),
            secondaryTint = Color(0xFF18181B)
        )
        id.contains("jack") || name.contains("jack daniel") -> MealVisualMeta(
            drawableRes = R.drawable.img_whiskey_spirits,
            icon = Icons.Default.Liquor,
            badgeLabel = "Old No. 7",
            accentColor = Color(0xFFF97316),
            secondaryTint = Color(0xFF431407)
        )
        id.contains("black_label") || name.contains("johnnie") -> MealVisualMeta(
            drawableRes = R.drawable.img_whiskey_spirits,
            icon = Icons.Default.Shield,
            badgeLabel = "Black Label",
            accentColor = Color(0xFFFBBF24),
            secondaryTint = Color(0xFF27272A)
        )
        id.contains("glenfiddich") || name.contains("glenfiddich") -> MealVisualMeta(
            drawableRes = R.drawable.img_whiskey_spirits,
            icon = Icons.Default.MilitaryTech,
            badgeLabel = "Single Malt",
            accentColor = Color(0xFFFFD700),
            secondaryTint = Color(0xFF365314)
        )
        id.contains("passion") || name.contains("passion") -> MealVisualMeta(
            drawableRes = R.drawable.img_cocktails,
            icon = Icons.Default.LocalCafe,
            badgeLabel = "Passion Mint",
            accentColor = Color(0xFFF43F5E),
            secondaryTint = Color(0xFF881337)
        )
        id.contains("mojito") || name.contains("mojito") -> MealVisualMeta(
            drawableRes = R.drawable.img_cocktails,
            icon = Icons.Default.Spa,
            badgeLabel = "Virgin Mint",
            accentColor = Color(0xFF34D399),
            secondaryTint = Color(0xFF064E3B)
        )
        id.contains("red_bull") || name.contains("energy") -> MealVisualMeta(
            drawableRes = R.drawable.img_cocktails,
            icon = Icons.Default.Bolt,
            badgeLabel = "Energy Cold",
            accentColor = Color(0xFF3B82F6),
            secondaryTint = Color(0xFF1E3A8A)
        )
        category == MenuCategory.COCKTAILS.name -> MealVisualMeta(
            drawableRes = R.drawable.img_cocktails,
            icon = Icons.Default.LocalBar,
            badgeLabel = "Mixology",
            accentColor = Amber500,
            secondaryTint = Zinc800
        )
        category == MenuCategory.BEERS_CIDERS.name -> MealVisualMeta(
            drawableRes = R.drawable.img_chilled_beers,
            icon = Icons.Default.SportsBar,
            badgeLabel = "Chilled Beer",
            accentColor = Amber400,
            secondaryTint = Zinc800
        )
        category == MenuCategory.WHISKEY_SPIRITS.name -> MealVisualMeta(
            drawableRes = R.drawable.img_whiskey_spirits,
            icon = Icons.Default.Liquor,
            badgeLabel = "Fine Spirit",
            accentColor = AmberGold,
            secondaryTint = Zinc800
        )
        category == MenuCategory.SIDES.name -> MealVisualMeta(
            drawableRes = R.drawable.img_sides_platter,
            icon = Icons.Default.RiceBowl,
            badgeLabel = "Side Dish",
            accentColor = EmeraldLive,
            secondaryTint = Zinc800
        )
        category == MenuCategory.WET_DRY_FRY.name -> MealVisualMeta(
            drawableRes = R.drawable.img_wet_fry,
            icon = Icons.Default.OutdoorGrill,
            badgeLabel = "Karai Fry",
            accentColor = GrillFlame,
            secondaryTint = Zinc800
        )
        else -> MealVisualMeta(
            drawableRes = R.drawable.img_nyama_choma,
            icon = Icons.Default.LocalFireDepartment,
            badgeLabel = "Charcoal Grill",
            accentColor = Amber500,
            secondaryTint = Zinc800
        )
    }
}

@Composable
fun MealVisual(
    dishId: String,
    dishName: String,
    category: String,
    modifier: Modifier = Modifier,
    overrideDrawableRes: Int? = null,
    showBadge: Boolean = true
) {
    val meta = getMealVisualMeta(dishId, dishName, category)
    val finalDrawable = overrideDrawableRes ?: meta.drawableRes

    Box(modifier = modifier) {
        // High quality photo for the meal
        Image(
            painter = painterResource(id = finalDrawable),
            contentDescription = dishName,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient scrim with dish-specific tint
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Zinc950.copy(alpha = 0.35f),
                            meta.secondaryTint.copy(alpha = 0.65f)
                        )
                    )
                )
        )

        // Distinct Dish Badge at Bottom-Left / Corner
        if (showBadge) {
            Surface(
                color = Zinc950.copy(alpha = 0.88f),
                shape = RoundedCornerShape(topEnd = 8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, meta.accentColor.copy(alpha = 0.6f)),
                modifier = Modifier.align(Alignment.BottomStart)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = meta.icon,
                        contentDescription = null,
                        tint = meta.accentColor,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = meta.badgeLabel,
                        color = Zinc100,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 9.sp
                    )
                }
            }
        }
    }
}
