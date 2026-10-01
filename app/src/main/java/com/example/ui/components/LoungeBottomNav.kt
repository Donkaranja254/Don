package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.outlined.EventSeat
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppTab
import com.example.ui.theme.*

data class NavTabItem(
    val tab: AppTab,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

@Composable
fun LoungeBottomNav(
    currentTab: AppTab,
    onTabSelected: (AppTab) -> Unit,
    cartCount: Int,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavTabItem(
            tab = AppTab.HOME,
            label = "Home",
            selectedIcon = Icons.Default.Home,
            unselectedIcon = Icons.Outlined.Home,
            testTag = "nav_home"
        ),
        NavTabItem(
            tab = AppTab.MENU,
            label = "Menu & Grill",
            selectedIcon = Icons.Default.RestaurantMenu,
            unselectedIcon = Icons.Outlined.RestaurantMenu,
            testTag = "nav_menu"
        ),
        NavTabItem(
            tab = AppTab.RESERVATIONS,
            label = "Reserve",
            selectedIcon = Icons.Default.EventSeat,
            unselectedIcon = Icons.Outlined.EventSeat,
            testTag = "nav_reservations"
        ),
        NavTabItem(
            tab = AppTab.FIND_US,
            label = "Find Us",
            selectedIcon = Icons.Default.Place,
            unselectedIcon = Icons.Outlined.Place,
            testTag = "nav_find_us"
        )
    )

    NavigationBar(
        containerColor = Zinc950,
        contentColor = Zinc100,
        tonalElevation = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Zinc800.copy(alpha = 0.8f))
            .navigationBarsPadding()
    ) {
        items.forEach { item ->
            val isSelected = currentTab == item.tab
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(item.tab) },
                icon = {
                    if (item.tab == AppTab.MENU && cartCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = Amber500,
                                    contentColor = Zinc950
                                ) {
                                    Text(
                                        text = cartCount.toString(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.label
                            )
                        }
                    } else {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.label
                        )
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Zinc950,
                    selectedTextColor = Amber400,
                    indicatorColor = Amber500,
                    unselectedIconColor = Zinc400,
                    unselectedTextColor = Zinc400
                ),
                modifier = Modifier.testTag(item.testTag)
            )
        }
    }
}
