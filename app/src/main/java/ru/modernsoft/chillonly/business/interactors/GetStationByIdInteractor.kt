package ru.modernsoft.chillonly.business.interactors

import ru.modernsoft.chillonly.data.models.Station

interface GetStationByIdInteractor {

    fun getStation(id: Long): Station
}