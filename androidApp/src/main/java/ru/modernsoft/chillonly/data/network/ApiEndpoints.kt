package ru.modernsoft.chillonly.data.network

import retrofit2.http.GET
import ru.modernsoft.chillonly.data.network.response.StationResponse
import rx.Observable

interface ApiEndpoints {

    @GET("stations")
//    fun stations(): Observable<List<Station>>
    fun stations(): Observable<List<StationResponse>>

}
