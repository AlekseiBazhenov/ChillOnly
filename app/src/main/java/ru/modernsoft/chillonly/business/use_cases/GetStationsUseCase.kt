package ru.modernsoft.chillonly.business.use_cases

import io.realm.OrderedRealmCollection
import ru.modernsoft.chillonly.data.models.Station

interface GetStationsUseCase {
    fun getStations(pageNumber: Int): OrderedRealmCollection<Station>?
}