package ru.modernsoft.chillonly.data.mapper

import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.data.network.response.StationResponse
import rx.Observable
import rx.functions.Func1

class StationResponseMapper : Func1<List<StationResponse>, List<Station>> {
    override fun call(list: List<StationResponse>): List<Station> {
        return Observable.from(list)
                .map { stationResponse ->
                    val station = Station()
                    station.id = stationResponse.id
                    station.title = stationResponse.title
                    station.description = stationResponse.description
                    station.playerUrl = stationResponse.playerUrl
                    station.stationUrl = stationResponse.stationUrl
                    station.image = stationResponse.image
                    station.created = stationResponse.created
                    station.updated = stationResponse.updated
                    station
                }
                .toList()
                .toBlocking()
                .first()
    }
}
