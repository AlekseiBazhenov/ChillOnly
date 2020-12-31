package ru.modernsoft.chillonly.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.modernsoft.chillonly.data.models.Favorite

@Dao
interface FavoriteDao {

    @Query("SELECT id FROM favorite")
    suspend fun getAllIds(): LongArray

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE id = :id)")
    suspend fun isStationFavorite(id: Long): Boolean

    @Query("SELECT * FROM favorite WHERE id = :id")
    suspend fun findById(id: Long): Favorite

    @Insert
    suspend fun insert(station: Favorite): Long

    @Delete
    suspend fun delete(station: Favorite): Int

}