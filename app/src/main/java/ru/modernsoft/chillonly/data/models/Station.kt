package ru.modernsoft.chillonly.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "station")
class Station(
        @PrimaryKey var id: Long = 0,
        var title: String = "",
        var description: String = "",
        var playerUrl: String = "",
        var stationUrl: String = "",
        var image: String = "",
        var created: String = "",
        var updated: String = "",
) : Serializable
