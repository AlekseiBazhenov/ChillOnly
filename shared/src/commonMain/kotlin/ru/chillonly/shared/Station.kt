package ru.chillonly.shared

import kotlinx.serialization.Serializable

@Serializable
data class Station(
    val id: Long,
    val title: String,
    val description: String,
    val player_url: String,
    val station_url: String,
    val image: String,
    val created: String,
    val updated: String
)