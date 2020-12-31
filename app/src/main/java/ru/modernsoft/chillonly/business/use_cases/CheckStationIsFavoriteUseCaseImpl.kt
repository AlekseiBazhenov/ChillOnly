package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl

class CheckStationIsFavoriteUseCaseImpl : CheckStationIsFavoriteUseCase {

    private val repo = StationsRepositoryImpl()

    override suspend fun isFavorite(station: Station): Boolean {
        return repo.isStationFavorite(station.id)
    }
}
