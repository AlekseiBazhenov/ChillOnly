package ru.modernsoft.chillonly.business.interactors

import io.realm.OrderedRealmCollection
import ru.modernsoft.chillonly.data.db.StationsFactory
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl
import rx.Observable

class GetStationsFromDbInteractorImpl : GetStationsFromDbInteractor {

//    private val repo = StationsRepositoryImpl()

    override fun loadStations(pageNumber: Int): OrderedRealmCollection<Station>? {
        return StationsFactory.getList(pageNumber)
    }
}
