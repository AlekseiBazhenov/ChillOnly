package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Station

interface CheckStationIsFavoriteUseCase {
    suspend fun isFavorite(station: Station): Boolean
}