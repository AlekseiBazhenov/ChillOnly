package ru.modernsoft.chillonly.business.interactors

import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl

class GetStationByIdInteractorImpl : GetStationByIdInteractor {

    private val repo = StationsRepositoryImpl()

    override fun getStation(id: Long): Station {
        return repo.getStationById(id)
    }
}
