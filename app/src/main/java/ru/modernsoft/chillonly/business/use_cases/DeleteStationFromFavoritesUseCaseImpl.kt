package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl

class DeleteStationFromFavoritesUseCaseImpl : DeleteStationFromFavoritesUseCase {

    private val repo = StationsRepositoryImpl()

    override suspend fun deleteFromFavorites(station: Station): Int {
        val favorite = repo.findById(station.id)
        return repo.deleteStationFromFavorites(favorite)
    }
}
