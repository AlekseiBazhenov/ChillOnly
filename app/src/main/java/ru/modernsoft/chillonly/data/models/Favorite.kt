package ru.modernsoft.chillonly.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite")
class Favorite(
    @PrimaryKey var id: Long = 0,
    var title: String = "",
) : Serializable
