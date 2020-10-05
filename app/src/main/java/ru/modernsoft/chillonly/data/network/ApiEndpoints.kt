package ru.modernsoft.chillonly.data.network

import retrofit2.http.GET
import ru.modernsoft.chillonly.data.network.response.StationResponse

interface ApiEndpoints {

    @GET("stations")
    suspend fun getStations(): List<StationResponse>

}
