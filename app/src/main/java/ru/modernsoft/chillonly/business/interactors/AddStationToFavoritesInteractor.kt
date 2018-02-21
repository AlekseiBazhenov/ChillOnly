package ru.modernsoft.chillonly.business.interactors

import ru.modernsoft.chillonly.data.models.Station

interface AddStationToFavoritesInteractor {

    fun addToFavorite(station: Station)
}