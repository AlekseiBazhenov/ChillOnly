package ru.modernsoft.chillonly.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.modernsoft.chillonly.data.models.Favorite
import ru.modernsoft.chillonly.data.models.Station

@Database(entities = [Station::class, Favorite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun stationDao(): StationDao
    abstract fun favoriteDao(): FavoriteDao


    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "app.db"
            ).build()
        }
    }
}