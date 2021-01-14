package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl

class GetFavoritesUseCase : UseCaseNoParams<List<Station>> {

    private val repo = StationsRepositoryImpl()

    override suspend fun doWork(): List<Station> {
        val favorites = repo.getFavorites()
        return repo.findByIds(favorites)
    }
}
