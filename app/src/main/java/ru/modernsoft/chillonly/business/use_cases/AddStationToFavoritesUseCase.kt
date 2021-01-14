package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Favorite
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl

class AddStationToFavoritesUseCase : UseCaseWithParams<Station, Long> {

    private val repo = StationsRepositoryImpl()

    override suspend fun doWork(params: Station): Long {
        val favorite = Favorite(params.id, params.title)
        return repo.addStationToFavorites(favorite)
    }
}
