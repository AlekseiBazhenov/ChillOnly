package ru.modernsoft.chillonly.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ru.modernsoft.chillonly.business.use_cases.*
import ru.modernsoft.chillonly.data.Resource
import ru.modernsoft.chillonly.data.models.Station

class FavoritesViewModel : ViewModel() {

    private val addStationToFavoritesUseCase = AddStationToFavoritesUseCase()
    private val deleteStationFromFavoritesUseCase = DeleteStationFromFavoritesUseCase()
    private val getFavoritesUseCase = GetFavoritesUseCase()
    private val checkStationIsFavoriteUseCase = CheckStationIsFavoriteUseCase()

    fun onAddToFavoritesClick(station: Station) = liveData(Dispatchers.IO) {
        try {
            val data = addStationToFavoritesUseCase.doWork(station)
            emit(Resource.success(data = data))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun onDeleteFromFavoritesClick(station: Station) = liveData(Dispatchers.IO) {
        try {
            val data = deleteStationFromFavoritesUseCase.doWork(station)
            emit(Resource.success(data = data))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getFavorites() = liveData(Dispatchers.IO) {
        try {
            val data = getFavoritesUseCase.doWork()
            emit(Resource.success(data = data))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun checkIsFavorite(station: Station) = liveData(Dispatchers.IO) {
        try {
            val data = checkStationIsFavoriteUseCase.doWork(station)
            emit(Resource.success(data = data))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}