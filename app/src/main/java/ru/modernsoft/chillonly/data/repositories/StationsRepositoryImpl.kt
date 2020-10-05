package ru.modernsoft.chillonly.data.repositories

import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.network.ApiService
import ru.modernsoft.chillonly.data.network.response.StationResponse

class StationsRepositoryImpl : StationsRepository {

    private val api = ApiService.getApi()

    override suspend fun getStations(): List<StationResponse> {
        return api.getStations()
    }

    override fun getStationById(id: Long): Station {
        return Station()
//        return db.where(Station::class.java)
//                .equalTo(DbFields.ID, id)
//                .findAll()
//                .first()!!
    }

//    override fun saveToCache(stations: List<Station>) {
//        db.executeTransaction {
//            for (station in stations) {
//                val favSt = db.where(FavStation::class.java)
//                        .equalTo(DbFields.ID, station.id)
//                        .equalTo(DbFields.STATION_TITLE, station.title)
//                        .findFirst()
//                station.isFav = favSt != null
//            }
//            db.insertOrUpdate(stations)
//        }
//    }

//    override fun addStationToFavorites(station: Station) {
//        db.executeTransaction {
//            val favSt = db.where(FavStation::class.java)
//                    .equalTo(DbFields.ID, station.id)
//                    .equalTo(DbFields.STATION_TITLE, station.title)
//                    .findFirst()
//            if (favSt == null) {
//                val fav = FavStation()
//                fav.id = station.id
//                fav.title = station.title
//                db.insert(fav)
//            } else {
//                favSt.deleteFromRealm()
//            }
//            station.isFav = !station.isFav
//        }
//    }
}