package ru.modernsoft.chillonly.business.interactors

import ru.modernsoft.chillonly.data.models.Station
import rx.Observable

interface GetStationsInteractor {

    fun loadStations(): Observable<List<Station>>
}