package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl

class CheckStationIsFavoriteUseCase : UseCaseWithParams<Station, Boolean> {

    private val repo = StationsRepositoryImpl()

    override suspend fun doWork(params: Station): Boolean {
        return repo.isStationFavorite(params.id)
    }
}
