package ru.modernsoft.chillonly.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.modernsoft.chillonly.data.models.Station

@Dao
interface StationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(stations: List<Station>): LongArray

    @Query("SELECT * FROM station WHERE id IN (:ids)")
    suspend fun findById(ids: LongArray): List<Station>


//    @Query("SELECT * FROM station")
//    fun getAll(): List<Station>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

//    @Delete
//    suspend fun delete(station: Station)
}