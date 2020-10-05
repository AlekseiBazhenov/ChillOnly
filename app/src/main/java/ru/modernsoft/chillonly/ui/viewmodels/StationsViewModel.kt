package ru.modernsoft.chillonly.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ru.modernsoft.chillonly.business.use_cases.LoadStationsUseCase
import ru.modernsoft.chillonly.business.use_cases.LoadStationsUseCaseImpl
import ru.modernsoft.chillonly.data.Resource

class StationsViewModel : ViewModel() {

    private val useCase: LoadStationsUseCase = LoadStationsUseCaseImpl()

//    val currentName: MutableLiveData<String> by lazy {
//        MutableLiveData<String>()
//    }

    fun getStations() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = useCase.loadStations()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}