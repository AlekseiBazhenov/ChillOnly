package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl

class GetFavoritesUseCaseImpl : GetFavoritesUseCase {

    private val repo = StationsRepositoryImpl()

    override suspend fun getFavorites(): List<Station> {
        val favorites = repo.getFavorites()
        return repo.findByIds(favorites)
    }
}
