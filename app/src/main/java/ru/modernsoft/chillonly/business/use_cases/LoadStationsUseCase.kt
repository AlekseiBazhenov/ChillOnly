package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Station
import rx.Observable

interface LoadStationsUseCase {

    suspend fun loadStations(): List<Station>
}