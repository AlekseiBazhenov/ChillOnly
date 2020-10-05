package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl

class LoadStationsUseCaseImpl : LoadStationsUseCase {

    private val repo = StationsRepositoryImpl()

    override suspend fun loadStations(): List<Station> {
        return repo.getStations().map {
            Station(
                it.id,
                it.title,
                it.description,
                it.playerUrl,
                it.stationUrl,
                it.image,
                it.created,
                it.updated
            )
        }
    }
}
