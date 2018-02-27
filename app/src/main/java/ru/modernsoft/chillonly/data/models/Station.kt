package ru.modernsoft.chillonly.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class Station(
        @PrimaryKey open var id: Long = 0,
        open var title: String = "",
        open var description: String = "",
        open var playerUrl: String = "",
        open var stationUrl: String = "",
        open var location: String = "",
        open var image: String = "",
        open var genres: String = "",
        open var recommended: Boolean = false,
        open var boughtPlace: Boolean = false,
        open var isFav: Boolean = false
) : RealmObject(), Serializable
