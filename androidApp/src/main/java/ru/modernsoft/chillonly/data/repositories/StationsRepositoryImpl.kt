package ru.modernsoft.chillonly.data.repositories

import io.realm.Realm
import ru.modernsoft.chillonly.data.db.DbFields
import ru.modernsoft.chillonly.data.mapper.StationResponseMapper
import ru.modernsoft.chillonly.data.models.FavStation
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.network.ApiService
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class StationsRepositoryImpl : StationsRepository {

    private val api = ApiService.getApi()
    private val db = Realm.getDefaultInstance()

    //todo inject db helper, api
//    val dbHelper = DatabaseHelper.get()

    override fun getStations(): Observable<List<Station>> {
        return api.stations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(StationResponseMapper())
    }

    override fun getStationById(id: Long): Station {
        return db.where(Station::class.java)
                .equalTo(DbFields.ID, id)
                .findAll()
                .first()!!
    }

    override fun saveToCache(stations: List<Station>) {
        db.executeTransaction {
            for (station in stations) {
                val favSt = db.where(FavStation::class.java)
                        .equalTo(DbFields.ID, station.id)
                        .equalTo(DbFields.STATION_TITLE, station.title)
                        .findFirst()
                station.isFav = favSt != null
            }
            db.insertOrUpdate(stations)
        }
    }

    override fun addStationToFavorites(station: Station) {
        db.executeTransaction {
            val favSt = db.where(FavStation::class.java)
                    .equalTo(DbFields.ID, station.id)
                    .equalTo(DbFields.STATION_TITLE, station.title)
                    .findFirst()
            if (favSt == null) {
                val fav = FavStation()
                fav.id = station.id
                fav.title = station.title
                db.insert(fav)
            } else {
                favSt.deleteFromRealm()
            }
            station.isFav = !station.isFav
        }
    }
}