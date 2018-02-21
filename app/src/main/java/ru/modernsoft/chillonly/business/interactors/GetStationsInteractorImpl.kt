package ru.modernsoft.chillonly.business.interactors

import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl
import rx.Observable

class GetStationsInteractorImpl : GetStationsInteractor {

    private val repo = StationsRepositoryImpl()

    override fun loadStations(): Observable<List<Station>> {
        return repo.getStations().doOnNext { repo.saveToCache(it) }
    }
}
