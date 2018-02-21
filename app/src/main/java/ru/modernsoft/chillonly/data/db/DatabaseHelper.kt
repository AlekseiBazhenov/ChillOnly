package ru.modernsoft.chillonly.data.db

import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmResults
import org.jetbrains.annotations.NotNull
import ru.modernsoft.chillonly.data.models.Station
import rx.Observable

class DatabaseHelper private constructor() {

    // todo inject realm
    val realm: Realm = Realm.getDefaultInstance()

    val allStations: OrderedRealmCollection<Station>
        get() = realm.where(Station::class.java)
                .findAll()

    val recommendedStations: OrderedRealmCollection<Station>
        get() = realm.where(Station::class.java)
                .equalTo(DbFields.STATION_RECOMMENDED, true)
                .findAll()

    val favoriteStations: RealmResults<Station>?
        get() = realm.where(Station::class.java)
                .equalTo(DbFields.STATION_IS_FAVORITE, true)
                .findAll()


//    fun find(@NotNull title: String): Observable<List<Station>> {
//        return realm.where(Station::class.java)
//                .like(DbFields.STATION_TITLE, title)
//                .findAll()
//                .asObservable()
//                .filter { it.isLoaded }
//                .map { realm.copyFromRealm(it) }
//    }

//    fun contain(@NotNull title: String): Boolean {
//        val size = realm.where(StationFav::class.java)
//                .equalTo("title", title)
//                .findAll()
//                .size
//        return size != 0
//    }

    companion object {

        private val INSTANCE = DatabaseHelper()

        fun get(): DatabaseHelper {
            return INSTANCE
        }
    }
}
