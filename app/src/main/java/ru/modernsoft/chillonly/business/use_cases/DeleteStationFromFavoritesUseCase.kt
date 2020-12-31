package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Station

interface DeleteStationFromFavoritesUseCase {
    suspend fun deleteFromFavorites(station: Station): Int
}