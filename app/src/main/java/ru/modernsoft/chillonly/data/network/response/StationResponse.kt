package ru.modernsoft.chillonly.data.network.response

import java.io.Serializable

open class StationResponse(
        open var id: Long = 0,
        open var title: String = "",
        open var description: String = "",
        open var playerUrl: String = "",
        open var stationUrl: String = "",
        open var location: String = "",
        open var image: String = "",
        open var genres: String = "",
        open var recommended: Boolean = false,
        open var bought_place: Boolean = false
) : Serializable
