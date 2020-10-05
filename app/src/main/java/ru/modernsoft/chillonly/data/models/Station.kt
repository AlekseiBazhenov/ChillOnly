package ru.modernsoft.chillonly.data.models

import java.io.Serializable

class Station(
        var id: Long = 0,
        var title: String = "",
        var description: String = "",
        var playerUrl: String = "",
        var stationUrl: String = "",
        var image: String = "",
        var created: String = "",
        var updated: String = "",
) : Serializable
