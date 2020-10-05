package ru.modernsoft.chillonly.data.mapper

import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.network.response.StationResponse

class StationsResponseMapper {
    fun execute(): (StationResponse) -> Station {
        return {
            Station(
                it.id,
                it.title,
                it.description,
                it.playerUrl,
                it.stationUrl,
                it.image,
                it.created,
                it.updated
            )
        }
    }
}
