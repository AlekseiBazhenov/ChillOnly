package ru.modernsoft.chillonly.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ru.modernsoft.chillonly.business.use_cases.LoadStationsUseCase
import ru.modernsoft.chillonly.business.use_cases.LoadStationsUseCaseImpl
import ru.modernsoft.chillonly.data.Resource

class StationsViewModel : ViewModel() {

    private val useCase: LoadStationsUseCase = LoadStationsUseCaseImpl()


    fun getStations() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val data = useCase.loadStations()
            emit(Resource.success(data = data))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}