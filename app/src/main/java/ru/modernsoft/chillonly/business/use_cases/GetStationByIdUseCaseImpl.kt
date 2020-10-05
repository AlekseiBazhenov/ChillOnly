package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl

class GetStationByIdUseCaseImpl : GetStationByIdUseCase {

    private val repo = StationsRepositoryImpl()

    override fun getStation(id: Long): Station {
        return repo.getStationById(id)
    }
}
