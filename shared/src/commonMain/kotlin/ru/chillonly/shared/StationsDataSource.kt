package ru.chillonly.shared

class StationsDataSource(private val api: StationsApi) {

    suspend fun getStations(): List<Station> {
        return api.getStations()
    }

}