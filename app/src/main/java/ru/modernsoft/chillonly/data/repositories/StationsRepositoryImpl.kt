package ru.modernsoft.chillonly.data.repositories

import ru.modernsoft.chillonly.ChillApp
import ru.modernsoft.chillonly.data.db.AppDatabase
import ru.modernsoft.chillonly.data.models.Favorite
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.network.ApiService
import ru.modernsoft.chillonly.data.network.response.StationResponse

class StationsRepositoryImpl : StationsRepository {

    private val api = ApiService.getApi()
    private val db = AppDatabase.getInstance(ChillApp.instance) // TODO: inject context


    override suspend fun getStations(): List<StationResponse> {
        return api.getStations()
    }


    override suspend fun saveToCache(stations: List<Station>): LongArray {
        return db.stationDao().insertAll(stations)
    }

    override suspend fun findByIds(ids: LongArray): List<Station> {
        return db.stationDao().findById(ids)
    }


    override suspend fun findById(id: Long): Favorite {
        return db.favoriteDao().findById(id)
    }

    override suspend fun addStationToFavorites(favorite: Favorite): Long {
        return db.favoriteDao().insert(favorite)
    }

    override suspend fun deleteStationFromFavorites(favorite: Favorite): Int {
        return db.favoriteDao().delete(favorite)
    }

    override suspend fun getFavorites(): LongArray {
        return db.favoriteDao().getAllIds()
    }

    override suspend fun isStationFavorite(id: Long): Boolean {
        return db.favoriteDao().isStationFavorite(id)
    }

}