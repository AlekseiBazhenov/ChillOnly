package ru.modernsoft.chillonly.data.repositories

import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.network.response.StationResponse

interface StationsRepository {
    suspend fun getStations(): List<StationResponse>
    fun getStationById(id: Long): Station?
//    fun saveToCache(stations: List<Station>)
//    fun addStationToFavorites(station: Station)
}

