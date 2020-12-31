package ru.modernsoft.chillonly.data.network.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class StationResponse(
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    @SerializedName("player_url") var playerUrl: String = "",
    @SerializedName("station_url") var stationUrl: String = "",
    var image: String = "",
    var created: String = "",
    var updated: String = "",
) : Serializable
