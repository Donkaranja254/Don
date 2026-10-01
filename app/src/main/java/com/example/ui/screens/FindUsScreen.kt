package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.TruceRepository
import com.example.model.CustomerReview
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun FindUsScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var feedbackName by remember { mutableStateOf("") }
    var feedbackMessage by remember { mutableStateOf("") }
    var feedbackSent by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title
        item {
            Column {
                Text(
                    text = "LOCATION & CONTACT",
                    color = Amber500,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Find Faraja Restaurant",
                    color = Zinc100,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Authentic African cuisine, sizzling nyama choma, and comfortable dining in Ruiru Town.",
                    color = Zinc400,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Location & Directions Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Zinc900),
                border = androidx.compose.foundation.BorderStroke(1.dp, Zinc800),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("location_card")
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_hero_lounge),
                            contentDescription = "Faraja Restaurant Location",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Zinc950.copy(alpha = 0.5f))
                        )

                        Surface(
                            color = Zinc950.copy(alpha = 0.85f),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                LiveOpenBadge()
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Amber500,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = TruceRepository.LOCATION_NAME,
                                    color = Zinc100,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = TruceRepository.LOCATION_LANDMARK,
                                    color = Zinc400,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = null,
                                tint = Amber500,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Phone: ${TruceRepository.DISPLAY_PHONE}",
                                color = Amber400,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = Amber500,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Hours: Open 24/7 (24 Hours Every Day)",
                                color = Zinc300,
                                fontSize = 13.sp
                            )
                        }

                        HorizontalDivider(color = Zinc800)

                        // 3 Quick contact action buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { launchDialer(context, TruceRepository.PHONE_NUMBER) },
                                colors = ButtonDefaults.buttonColors(containerColor = Amber500),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = null,
                                    tint = Zinc950,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Call", color = Zinc950, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }

                            Button(
                                onClick = { launchWhatsApp(context, "Hello Faraja Restaurant, I need directions/inquiry.") },
                                colors = ButtonDefaults.buttonColors(containerColor = Zinc800),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Chat,
                                    contentDescription = null,
                                    tint = EmeraldLive,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("WhatsApp", color = Zinc100, fontSize = 13.sp)
                            }

                            Button(
                                onClick = { launchMaps(context) },
                                colors = ButtonDefaults.buttonColors(containerColor = Zinc800),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Directions,
                                    contentDescription = null,
                                    tint = Amber400,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Maps", color = Zinc100, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }
        }

        // Amenities & Perks
        item {
            Text(
                text = "FACILITIES & COMFORT",
                color = Amber500,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Text(
                text = "What You'll Enjoy",
                color = Zinc100,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                AmenityRow(
                    icon = Icons.Default.LocalParking,
                    title = "Secure & Ample Parking",
                    description = "Dedicated guarded compound with 24/7 security & valet assistance."
                )
                AmenityRow(
                    icon = Icons.Default.OutdoorGrill,
                    title = "24/7 Live Charcoal Grill",
                    description = "Fresh juicy pork, succulent mbuzi, and chicken prepared round the clock."
                )
                AmenityRow(
                    icon = Icons.Default.Tv,
                    title = "4K Big Screen Matchdays",
                    description = "Live EPL, Champions League, Formula 1, and boxing match streaming."
                )
                AmenityRow(
                    icon = Icons.Default.LocalFireDepartment,
                    title = "Cozy Firepit Garden & Pergolas",
                    description = "Outdoor seating with gentle heating for Nairobi's cool evenings."
                )
                AmenityRow(
                    icon = Icons.Default.Wifi,
                    title = "Free High-Speed Wi-Fi",
                    description = "Fast seamless internet throughout the lounge and garden terrace."
                )
            }
        }

        // Customer Reviews
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "COMMUNITY REVIEWS",
                color = Amber500,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Text(
                text = "What Guests Say",
                color = Zinc100,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        items(TruceRepository.customerReviews) { review ->
            ReviewCard(review = review)
        }

        // Feedback / Special Request Box
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Zinc900),
                border = androidx.compose.foundation.BorderStroke(1.dp, Zinc800),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Send Inquiry or Feedback",
                        color = Zinc100,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Planning a party, corporate event, or want to speak with management? Drop us a line.",
                        color = Zinc400,
                        fontSize = 12.sp
                    )

                    OutlinedTextField(
                        value = feedbackName,
                        onValueChange = { feedbackName = it },
                        label = { Text("Your Name", color = Zinc400) },
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
                        value = feedbackMessage,
                        onValueChange = { feedbackMessage = it },
                        label = { Text("Your Message", color = Zinc400) },
                        placeholder = { Text("Tell us your request...", color = Zinc600) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Amber500,
                            unfocusedBorderColor = Zinc700,
                            focusedTextColor = Zinc100,
                            unfocusedTextColor = Zinc100
                        ),
                        shape = RoundedCornerShape(10.dp),
                        minLines = 3,
                        modifier = Modifier.fillMaxWidth()
                    )

                    AmberGradientButton(
                        text = "Send via WhatsApp",
                        leadingIcon = Icons.Default.Send,
                        onClick = {
                            val msg = "Hello Faraja Restaurant,\nFrom: ${feedbackName.ifBlank { "Guest" }}\nMessage: $feedbackMessage"
                            launchWhatsApp(context, msg)
                            feedbackSent = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (feedbackSent) {
                        Text(
                            text = "Thank you! Opening WhatsApp to send your message to Faraja Restaurant.",
                            color = EmeraldLive,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Footer copyright
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "FARAJA RESTAURANT",
                    color = Amber500,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "© 2026 Faraja Restaurant. All rights reserved.",
                    color = Zinc600,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

@Composable
fun AmenityRow(
    icon: ImageVector,
    title: String,
    description: String
) {
    Surface(
        color = Zinc900,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Zinc800),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Zinc850),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Amber500,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Zinc100,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Text(
                    text = description,
                    color = Zinc400,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }
        }
    }
}

@Composable
fun ReviewCard(review: CustomerReview) {
    Surface(
        color = Zinc900,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Zinc800),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Amber500.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = review.author.take(1),
                            color = Amber400,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = review.author,
                            color = Zinc100,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = review.date,
                            color = Zinc500,
                            fontSize = 10.sp
                        )
                    }
                }

                RatingStars(rating = review.rating.toDouble())
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = review.comment,
                color = Zinc300,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Surface(
                color = Zinc850,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "Recommended: ${review.favoriteItem}",
                    color = Amber400,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }
    }
}
