package ru.modernsoft.chillonly.data.db

import io.realm.OrderedRealmCollection
import ru.modernsoft.chillonly.data.models.Station

object StationsFactory {

    fun getList(page: Int): OrderedRealmCollection<Station>? {
        when (page) {
            0 -> return DatabaseHelper.get().allStations
            1 -> return DatabaseHelper.get().recommendedStations
            2 -> return DatabaseHelper.get().favoriteStations
        }
        return null
    }
}
