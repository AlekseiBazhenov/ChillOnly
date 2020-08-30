package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Station

interface AddStationToFavoritesUseCase {

    fun addToFavorite(station: Station)
}