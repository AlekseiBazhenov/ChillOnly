package ru.modernsoft.chillonly.business.interactors

import io.realm.OrderedRealmCollection
import ru.modernsoft.chillonly.data.models.Station

interface GetStationsFromDbInteractor {
    fun loadStations(pageNumber: Int): OrderedRealmCollection<Station>?
}