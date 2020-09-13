package ru.chillonly.shared

import commonMain.kotlin.ru.chillonly.shared.network.StationsApi
import commonMain.kotlin.ru.chillonly.shared.network.response.Station

class StationsDataSource(private val api: StationsApi) {

    suspend fun getStations(): List<Station> {
        return api.getStations()
    }

}