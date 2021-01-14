package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl

class DeleteStationFromFavoritesUseCase : UseCaseWithParams<Station, Int> {

    private val repo = StationsRepositoryImpl()

    override suspend fun doWork(params: Station): Int {
        val favorite = repo.findById(params.id)
        return repo.deleteStationFromFavorites(favorite)
    }
}
