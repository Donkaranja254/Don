package com.example.model

enum class SeatingArea(val label: String, val description: String) {
    MAIN_LOUNGE("Main Lounge", "Vibrant ambient lounge with music & bar views"),
    OUTDOOR_GARDEN("Outdoor Pergola & Garden", "Spacious open-air with warm firepits"),
    VIP_BALCONY("VIP Balcony", "Elevated panoramic booth with dedicated waiter service"),
    SPORTS_SCREEN("Sports Big Screen", "Best seats facing the 4K projector screens")
}

enum class Occasion(val label: String) {
    CASUAL_CHOMA("Casual Nyama Choma Hangout"),
    BIRTHDAY("Birthday Celebration"),
    MATCH_NIGHT("Match Night / Watch Party"),
    CORPORATE("Corporate Dinner / Business"),
    DATE_NIGHT("Date Night & Cocktails"),
    LATE_NIGHT("Late Night 24/7 Gathering")
}

data class Reservation(
    val id: String,
    val guestName: String,
    val guestPhone: String,
    val guestsCount: Int,
    val dateText: String,
    val timeSlotText: String,
    val seatingArea: SeatingArea,
    val occasion: Occasion,
    val specialNotes: String = "",
    val confirmationCode: String,
    val createdAtEpoch: Long = System.currentTimeMillis(),
    val status: String = "Confirmed"
)
