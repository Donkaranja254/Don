package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.TruceRepository
import com.example.model.LoungeEvent
import com.example.model.MenuItem
import com.example.ui.AppTab
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    onNavigateToTab: (AppTab) -> Unit,
    onSelectItem: (MenuItem) -> Unit,
    onQuickAddToCart: (MenuItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 90.dp)
    ) {
        // Hero Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_hero_lounge),
                contentDescription = "Faraja Restaurant Ambiance",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Dark gradient overlay matching the web style zinc-950/75
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Zinc950.copy(alpha = 0.5f),
                                Zinc950.copy(alpha = 0.85f),
                                Zinc950
                            )
                        )
                    )
            )

            // Hero Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    color = Amber500.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(20.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Amber500.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = "FARAJA RESTAURANT & GRILL",
                        color = Amber400,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.2.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Authentic African Cuisine & Flame Grills",
                    color = Zinc100,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Experience mouthwatering Nyama Choma, signature African stews, fresh grills, and comfortable dining in Ruiru Town.",
                    color = Zinc300,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Hero Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AmberGradientButton(
                        text = "Explore Menu",
                        leadingIcon = Icons.Default.RestaurantMenu,
                        onClick = { onNavigateToTab(AppTab.MENU) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("hero_explore_menu_button")
                    )

                    OutlinedButton(
                        onClick = { onNavigateToTab(AppTab.FIND_US) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Zinc900.copy(alpha = 0.7f),
                            contentColor = Zinc100
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Zinc700),
                        modifier = Modifier
                            .weight(1f)
                            .defaultMinSize(minHeight = 48.dp)
                            .testTag("hero_get_directions_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Directions,
                            contentDescription = null,
                            tint = Amber400,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Directions",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        // Quick Action Strip
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuickActionTile(
                title = "Reserve Table",
                subtitle = "Instant Pass",
                icon = Icons.Default.EventSeat,
                onClick = { onNavigateToTab(AppTab.RESERVATIONS) },
                modifier = Modifier.weight(1f)
            )

            QuickActionTile(
                title = "Call Counter",
                subtitle = "+254 110 051967",
                icon = Icons.Default.PhoneInTalk,
                onClick = { launchDialer(context, TruceRepository.PHONE_NUMBER) },
                modifier = Modifier.weight(1f)
            )

            QuickActionTile(
                title = "WhatsApp",
                subtitle = "Chat Live",
                icon = Icons.Default.Chat,
                onClick = { launchWhatsApp(context, "Hello Faraja Restaurant, I want to reserve a table/place an order.") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Three Highlights Cards (from the HTML website)
        Text(
            text = "WHY FARAJA RESTAURANT",
            color = Amber500,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Text(
            text = "Crafted for Delicious Dining & Comfort",
            color = Zinc100,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FeatureHighlightCard(
                stepNumber = "01",
                title = "Signature Nyama Choma",
                description = "Freshly prepared juicy pork, succulent mbuzi, and local chicken grilled to absolute perfection.",
                icon = Icons.Default.LocalFireDepartment,
                accentColor = GrillFlame
            )

            FeatureHighlightCard(
                stepNumber = "02",
                title = "Master Mixology",
                description = "Indulge in expertly blended Long Island iced teas, Whiskey Sours, and specialized house cocktails.",
                icon = Icons.Default.LocalBar,
                accentColor = Amber500
            )

            FeatureHighlightCard(
                stepNumber = "03",
                title = "Spacious & Secure",
                description = "Enjoy ample parking space, comfortable outdoor seating, and a peaceful environment day or night.",
                icon = Icons.Default.Security,
                accentColor = EmeraldLive
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Popular Selections (The 4 core dishes featured on HTML)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "HOT ON THE GRILL & BAR",
                    color = Amber500,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Popular Selections",
                    color = Zinc100,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            TextButton(onClick = { onNavigateToTab(AppTab.MENU) }) {
                Text(
                    text = "Full Menu",
                    color = Amber400,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Amber400,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Popular items row
        val popularList = TruceRepository.menuItems.filter { it.isPopular }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(popularList) { item ->
                PopularItemCard(
                    item = item,
                    onClick = { onSelectItem(item) },
                    onQuickAdd = { onQuickAddToCart(item) }
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Weekly Nights & Entertainment
        Text(
            text = "VIBE & ENTERTAINMENT",
            color = Amber500,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Text(
            text = "Faraja Dining & Events Schedule",
            color = Zinc100,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TruceRepository.events.forEach { event ->
                EventCard(event = event)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Visit Callout Banner
        Surface(
            color = Zinc900,
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Zinc800),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        tint = Amber500,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Visit Faraja Restaurant Today",
                        color = Zinc100,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Ruiru Town, Kiambu County, Kenya (Next to National Bank)\nBreakfast, Lunch, Dinner & Refreshments",
                    color = Zinc400,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { launchDialer(context, TruceRepository.PHONE_NUMBER) },
                        colors = ButtonDefaults.buttonColors(containerColor = Amber500),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .defaultMinSize(minHeight = 44.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = null,
                            tint = Zinc950,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Call to Reserve",
                            color = Zinc950,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }

                    OutlinedButton(
                        onClick = { launchMaps(context) },
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Zinc700),
                        modifier = Modifier
                            .weight(1f)
                            .defaultMinSize(minHeight = 44.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Map,
                            contentDescription = null,
                            tint = Amber400,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Get Map",
                            color = Zinc200,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActionTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        color = Zinc900,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Zinc800),
        modifier = modifier.defaultMinSize(minHeight = 64.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Amber500,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                color = Zinc100,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                color = Zinc400,
                fontSize = 10.sp,
                maxLines = 1
            )
        }
    }
}

@Composable
fun FeatureHighlightCard(
    stepNumber: String,
    title: String,
    description: String,
    icon: ImageVector,
    accentColor: Color
) {
    Surface(
        color = Zinc900,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Zinc800),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentColor.copy(alpha = 0.15f))
                    .border(1.dp, accentColor.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stepNumber,
                    color = accentColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = title,
                        color = Zinc100,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    color = Zinc400,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun PopularItemCard(
    item: MenuItem,
    onClick: () -> Unit,
    onQuickAdd: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Zinc900),
        border = androidx.compose.foundation.BorderStroke(1.dp, Zinc800),
        modifier = Modifier
            .width(230.dp)
            .height(270.dp)
            .testTag("popular_card_${item.id}")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(135.dp)
            ) {
                MealVisual(
                    dishId = item.id,
                    dishName = item.name,
                    category = item.category.name,
                    overrideDrawableRes = item.drawableRes,
                    showBadge = true,
                    modifier = Modifier.fillMaxSize()
                )

                if (item.tag != null) {
                    Surface(
                        color = Amber500,
                        shape = RoundedCornerShape(bottomEnd = 10.dp),
                        modifier = Modifier.align(Alignment.TopStart)
                    ) {
                        Text(
                            text = item.tag,
                            color = Zinc950,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = item.name,
                        color = Zinc100,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 1
                    )
                    Text(
                        text = item.description,
                        color = Zinc400,
                        fontSize = 11.sp,
                        lineHeight = 14.sp,
                        maxLines = 2,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "KES ${"%,d".format(item.priceKes)}",
                            color = Amber400,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp
                        )
                        RatingStars(rating = item.rating)
                    }

                    IconButton(
                        onClick = onQuickAdd,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Amber500)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Quick add to tab",
                            tint = Zinc950,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EventCard(event: LoungeEvent) {
    Surface(
        color = Zinc900,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Zinc800),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(70.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Zinc850)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = event.scheduleDay.substringBefore(" "),
                    color = Amber400,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = event.badge,
                    color = Zinc400,
                    fontSize = 9.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title,
                    color = Zinc100,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = event.headline,
                    color = Zinc400,
                    fontSize = 12.sp,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = Amber500,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = event.time,
                        color = Amber500,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = " • ${event.perk}",
                        color = Zinc400,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}
