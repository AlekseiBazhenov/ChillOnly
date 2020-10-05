package ru.modernsoft.chillonly.data.network.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class StationResponse(
        open var id: Long = 0,
        open var title: String = "",
        open var description: String = "",
        @SerializedName("player_url") open var playerUrl: String = "",
        @SerializedName("station_url") open var stationUrl: String = "",
        open var image: String = "",
        open var created: String = "",
        open var updated: String = "",
) : Serializable
