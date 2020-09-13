package ru.chillonly.shared

import commonMain.kotlin.ru.chillonly.shared.network.StationsApi
import commonMain.kotlin.ru.chillonly.shared.network.response.Station

class GetStationsUseCase(private val dataSource: StationsDataSource) {

    suspend fun getStations(): List<Station> {
        val stations = dataSource.getStations()
        return stations
    }

    suspend fun getStationById(id: Long) : Station {
        val stations = dataSource.getStations()
        stations.forEach {
            if (it.id == id) {
                return it
            }
        }
        return stations[0]
    }

    // TODO DI instead
    object CaseProvider {
        fun getCase() = GetStationsUseCase(StationsDataSource(StationsApi()))
    }
}