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
                    station.location = stationResponse.location
                    station.image = stationResponse.image
                    station.genres = stationResponse.genres
                    station.recommended = stationResponse.recommended
                    station.location = stationResponse.location
                    station.boughtPlace = stationResponse.bought_place
                    station
                }
                .toList()
                .toBlocking()
                .first()
    }
}
