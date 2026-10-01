package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TruceRepository
import com.example.model.Occasion
import com.example.model.Reservation
import com.example.model.SeatingArea
import com.example.ui.UiState
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun ReservationsScreen(
    uiState: UiState,
    onCreateReservation: (
        name: String,
        phone: String,
        guests: Int,
        date: String,
        timeSlot: String,
        area: SeatingArea,
        occasion: Occasion,
        notes: String
    ) -> Unit,
    onDismissSuccessDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var guestName by remember { mutableStateOf("") }
    var guestPhone by remember { mutableStateOf("") }
    var guestsCount by remember { mutableStateOf(4) }
    var selectedDate by remember { mutableStateOf("Tonight") }
    var selectedTimeSlot by remember { mutableStateOf("8:00 PM") }
    var selectedArea by remember { mutableStateOf(SeatingArea.OUTDOOR_GARDEN) }
    var selectedOccasion by remember { mutableStateOf(Occasion.CASUAL_CHOMA) }
    var specialNotes by remember { mutableStateOf("") }

    val dateOptions = listOf("Tonight", "Tomorrow", "Friday Night", "Saturday Vibe", "Sunday Chill")
    val timeOptions = listOf("6:00 PM", "7:30 PM", "8:30 PM", "10:00 PM", "Midnight 24/7", "2:00 AM")
    val guestCounts = listOf(2, 4, 6, 8, 10, 15, 20)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title banner
        item {
            Column {
                Text(
                    text = "TABLE & VIP RESERVATIONS",
                    color = Amber500,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Reserve Your Faraja Dining Experience",
                    color = Zinc100,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Enjoy comfortable seating, pre-ordered Nyama Choma, and delicious dining in Ruiru Town.",
                    color = Zinc400,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Reservation Form Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Zinc900),
                border = androidx.compose.foundation.BorderStroke(1.dp, Zinc800),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Booking Details",
                        color = Amber400,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // Guest Name
                    OutlinedTextField(
                        value = guestName,
                        onValueChange = { guestName = it },
                        label = { Text("Your Full Name", color = Zinc400) },
                        placeholder = { Text("e.g. Dennis K.", color = Zinc600) },
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = Amber500)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Amber500,
                            unfocusedBorderColor = Zinc700,
                            focusedTextColor = Zinc100,
                            unfocusedTextColor = Zinc100
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("reservation_name_input")
                    )

                    // Phone Number
                    OutlinedTextField(
                        value = guestPhone,
                        onValueChange = { guestPhone = it },
                        label = { Text("Phone Number", color = Zinc400) },
                        placeholder = { Text("e.g. +254 712 345678", color = Zinc600) },
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = Amber500)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Amber500,
                            unfocusedBorderColor = Zinc700,
                            focusedTextColor = Zinc100,
                            unfocusedTextColor = Zinc100
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("reservation_phone_input")
                    )

                    // Number of Guests
                    Column {
                        Text(
                            text = "Number of Guests: $guestsCount people",
                            color = Zinc300,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(guestCounts) { count ->
                                val isSelected = guestsCount == count
                                Surface(
                                    onClick = { guestsCount = count },
                                    color = if (isSelected) Amber500 else Zinc800,
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.defaultMinSize(minWidth = 42.dp)
                                ) {
                                    Text(
                                        text = "$count",
                                        color = if (isSelected) Zinc950 else Zinc300,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Date Selection
                    Column {
                        Text(
                            text = "Reservation Date",
                            color = Zinc300,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(dateOptions) { date ->
                                val isSelected = selectedDate == date
                                Surface(
                                    onClick = { selectedDate = date },
                                    color = if (isSelected) Amber500 else Zinc800,
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = date,
                                        color = if (isSelected) Zinc950 else Zinc300,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Time Slot Selection
                    Column {
                        Text(
                            text = "Preferred Time Slot (Open 24/7)",
                            color = Zinc300,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(timeOptions) { slot ->
                                val isSelected = selectedTimeSlot == slot
                                Surface(
                                    onClick = { selectedTimeSlot = slot },
                                    color = if (isSelected) Amber500 else Zinc800,
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = slot,
                                        color = if (isSelected) Zinc950 else Zinc300,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Seating Area
                    Column {
                        Text(
                            text = "Preferred Seating Area",
                            color = Zinc300,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        SeatingArea.values().forEach { area ->
                            val isSelected = selectedArea == area
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) Zinc800 else Zinc850)
                                    .border(
                                        1.dp,
                                        if (isSelected) Amber500 else Zinc800,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable { selectedArea = area }
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = area.label,
                                        color = if (isSelected) Amber400 else Zinc200,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = area.description,
                                        color = Zinc400,
                                        fontSize = 11.sp
                                    )
                                }
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedArea = area },
                                    colors = RadioButtonDefaults.colors(selectedColor = Amber500)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }

                    // Special Requests
                    OutlinedTextField(
                        value = specialNotes,
                        onValueChange = { specialNotes = it },
                        label = { Text("Special Requests / Dietary Notes", color = Zinc400) },
                        placeholder = { Text("e.g. 2kg Mbuzi pre-ordered, birthday cake, firepit table", color = Zinc600) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Amber500,
                            unfocusedBorderColor = Zinc700,
                            focusedTextColor = Zinc100,
                            unfocusedTextColor = Zinc100
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    AmberGradientButton(
                        text = "Confirm & Generate Pass",
                        leadingIcon = Icons.Default.ConfirmationNumber,
                        onClick = {
                            val name = if (guestName.isNotBlank()) guestName else "Lounge Guest"
                            val phone = if (guestPhone.isNotBlank()) guestPhone else TruceRepository.DISPLAY_PHONE
                            onCreateReservation(
                                name,
                                phone,
                                guestsCount,
                                selectedDate,
                                selectedTimeSlot,
                                selectedArea,
                                selectedOccasion,
                                specialNotes
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("submit_reservation_button")
                    )
                }
            }
        }

        // Active & Saved Passes Section
        if (uiState.reservations.isNotEmpty()) {
            item {
                Text(
                    text = "YOUR RESERVATION PASSES",
                    color = Amber500,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Confirmed Bookings",
                    color = Zinc100,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            items(uiState.reservations) { res ->
                ReservationTicketCard(
                    reservation = res,
                    onSendWhatsApp = {
                        val msg = """
                            Hello Faraja Restaurant!
                            Confirming table booking:
                            • Pass Code: ${res.confirmationCode}
                            • Guest: ${res.guestName}
                            • Guests: ${res.guestsCount}
                            • Date & Time: ${res.dateText} at ${res.timeSlotText}
                            • Area: ${res.seatingArea.label}
                        """.trimIndent()
                        launchWhatsApp(context, msg)
                    },
                    onCallToConfirm = { launchDialer(context, TruceRepository.PHONE_NUMBER) }
                )
            }
        }
    }

    // Success Dialog
    if (uiState.showReservationSuccessDialog && uiState.lastConfirmedReservation != null) {
        val res = uiState.lastConfirmedReservation
        AlertDialog(
            onDismissRequest = onDismissSuccessDialog,
            containerColor = Zinc900,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = EmeraldLive,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Reservation Confirmed!",
                        color = Zinc100,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Booking Reference: ${res.confirmationCode}",
                        color = Amber400,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Table for ${res.guestsCount} guests at ${res.seatingArea.label} on ${res.dateText} (${res.timeSlotText}).",
                        color = Zinc300,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "We have reserved your table at Faraja Restaurant, Ruiru Town. Feel free to send your pass to WhatsApp to pre-order food or refreshments.",
                        color = Zinc400,
                        fontSize = 12.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDismissSuccessDialog()
                        val msg = """
                            Hello Faraja Restaurant, confirming my booking:
                            • Pass Code: ${res.confirmationCode}
                            • Guest: ${res.guestName}
                            • Date: ${res.dateText} at ${res.timeSlotText}
                        """.trimIndent()
                        launchWhatsApp(context, msg)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Amber500)
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = null,
                        tint = Zinc950,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("WhatsApp Pass", color = Zinc950, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissSuccessDialog) {
                    Text("Close", color = Zinc400)
                }
            }
        )
    }
}

@Composable
fun ReservationTicketCard(
    reservation: Reservation,
    onSendWhatsApp: () -> Unit,
    onCallToConfirm: () -> Unit
) {
    Surface(
        color = Zinc900,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Amber500.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Amber500.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ConfirmationNumber,
                            contentDescription = null,
                            tint = Amber500,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = reservation.confirmationCode,
                            color = Amber400,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Faraja Restaurant • Ruiru Town",
                            color = Zinc500,
                            fontSize = 11.sp
                        )
                    }
                }

                Surface(
                    color = EmeraldLive.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = reservation.status,
                        color = EmeraldLive,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Zinc800)
            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Guest Name", color = Zinc500, fontSize = 11.sp)
                    Text(text = reservation.guestName, color = Zinc100, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                Column {
                    Text(text = "Party Size", color = Zinc500, fontSize = 11.sp)
                    Text(text = "${reservation.guestsCount} Guests", color = Zinc100, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                Column {
                    Text(text = "Date & Time", color = Zinc500, fontSize = 11.sp)
                    Text(text = "${reservation.dateText}, ${reservation.timeSlotText}", color = Amber400, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Area: ${reservation.seatingArea.label}",
                color = Zinc300,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            if (reservation.specialNotes.isNotBlank()) {
                Text(
                    text = "Notes: ${reservation.specialNotes}",
                    color = Zinc400,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onSendWhatsApp,
                    colors = ButtonDefaults.buttonColors(containerColor = Zinc800),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = null,
                        tint = EmeraldLive,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("WhatsApp", color = Zinc200, fontSize = 12.sp)
                }

                Button(
                    onClick = onCallToConfirm,
                    colors = ButtonDefaults.buttonColors(containerColor = Amber500),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                        tint = Zinc950,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Call Desk", color = Zinc950, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}
