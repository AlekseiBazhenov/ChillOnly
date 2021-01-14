package ru.modernsoft.chillonly.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.modernsoft.chillonly.business.use_cases.UseCaseNoParams
import ru.modernsoft.chillonly.data.Resource
import ru.modernsoft.chillonly.data.models.Station

class StationsViewModel(
    private val useCase: UseCaseNoParams<List<Station>>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun getStations() = liveData(dispatcher) {
        emit(Resource.loading(data = null))
        try {
            val data = useCase.doWork()
            emit(Resource.success(data = data))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}