package ru.modernsoft.chillonly.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class FavStation(
        @PrimaryKey open var id: Long = 0,
        open var title: String = ""
) : RealmObject(), Serializable
