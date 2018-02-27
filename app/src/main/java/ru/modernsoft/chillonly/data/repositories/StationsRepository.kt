package ru.modernsoft.chillonly.data.repositories

import ru.modernsoft.chillonly.data.models.Station
import rx.Observable

interface StationsRepository {
    fun getStations(): Observable<List<Station>>
    fun getStationById(id: Long): Station?
    fun saveToCache(stations: List<Station>)
    fun addStationToFavorites(station: Station)
}

