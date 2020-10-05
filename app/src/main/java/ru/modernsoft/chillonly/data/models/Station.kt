package ru.modernsoft.chillonly.data.models

import java.io.Serializable

open class Station(
        open var id: Long = 0,
        open var title: String = "",
        open var description: String = "",
        open var playerUrl: String = "",
        open var stationUrl: String = "",
        open var image: String = "",
        open var created: String = "",
        open var updated: String = "",
) : Serializable
