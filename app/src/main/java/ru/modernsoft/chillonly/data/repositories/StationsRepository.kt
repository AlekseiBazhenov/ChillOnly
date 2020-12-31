package ru.modernsoft.chillonly.data.repositories

import ru.modernsoft.chillonly.data.models.Favorite
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.network.response.StationResponse

interface StationsRepository {

    suspend fun getStations(): List<StationResponse>
    suspend fun saveToCache(stations: List<Station>): LongArray
    suspend fun findByIds(ids: LongArray): List<Station>
    suspend fun findById(id: Long): Favorite

    suspend fun addStationToFavorites(favorite: Favorite): Long
    suspend fun deleteStationFromFavorites(favorite: Favorite): Int
    suspend fun getFavorites(): LongArray
    suspend fun isStationFavorite(id: Long): Boolean

}

