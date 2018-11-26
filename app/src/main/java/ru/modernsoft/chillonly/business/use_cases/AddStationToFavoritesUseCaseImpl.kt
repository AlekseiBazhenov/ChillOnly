package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl

class AddStationToFavoritesUseCaseImpl : AddStationToFavoritesUseCase {

    private val repo = StationsRepositoryImpl()

    override fun addToFavorite(station: Station) {
        repo.addStationToFavorites(station)
    }
}
