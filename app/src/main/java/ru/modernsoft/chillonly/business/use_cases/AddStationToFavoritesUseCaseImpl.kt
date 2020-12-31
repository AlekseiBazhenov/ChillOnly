package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Favorite
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl

class AddStationToFavoritesUseCaseImpl : AddStationToFavoritesUseCase {

    private val repo = StationsRepositoryImpl()

    override suspend fun addToFavorites(station: Station): Long {
        val favorite = Favorite(station.id, station.title)
        return repo.addStationToFavorites(favorite)
    }
}
