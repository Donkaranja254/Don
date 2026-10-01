package com.example.model

data class LoungeEvent(
    val id: String,
    val title: String,
    val scheduleDay: String,
    val time: String,
    val headline: String,
    val badge: String,
    val perk: String
)

data class CustomerReview(
    val id: String,
    val author: String,
    val rating: Int,
    val comment: String,
    val date: String,
    val favoriteItem: String
)
