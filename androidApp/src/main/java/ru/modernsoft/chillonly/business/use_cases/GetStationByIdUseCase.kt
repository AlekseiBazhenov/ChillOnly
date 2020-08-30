package ru.modernsoft.chillonly.business.use_cases

import ru.modernsoft.chillonly.data.models.Station

interface GetStationByIdUseCase {

    fun getStation(id: Long): Station
}