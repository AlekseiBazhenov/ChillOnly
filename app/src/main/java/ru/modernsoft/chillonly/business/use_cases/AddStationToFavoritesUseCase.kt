package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Station

interface AddStationToFavoritesUseCase {
    suspend fun addToFavorites(station: Station): Long
}