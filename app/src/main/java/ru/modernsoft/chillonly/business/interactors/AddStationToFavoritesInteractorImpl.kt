package ru.modernsoft.chillonly.business.interactors

import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl

class AddStationToFavoritesInteractorImpl : AddStationToFavoritesInteractor {

    private val repo = StationsRepositoryImpl()

    override fun addToFavorite(station: Station) {
        repo.addStationToFavorites(station)
    }
}
