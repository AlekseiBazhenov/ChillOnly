package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.mapper.StationsResponseMapper
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl

class LoadStationsUseCase : UseCaseNoParams<List<Station>> {

    private val repo = StationsRepositoryImpl()

    override suspend fun doWork(): List<Station> {
        val response = repo.getStations()
        val stations = response.map(StationsResponseMapper().execute())
        repo.saveToCache(stations)
        return stations
    }
}
