package ru.chillonly.shared

class GetStationsUseCase(private val dataSource: StationsDataSource) {

    suspend fun getStations(): List<Station> {
        return dataSource.getStations()
    }

    // TODO DI instead
    object CaseProvider {
        fun getCase() = GetStationsUseCase(StationsDataSource(StationsApi()))
    }
}