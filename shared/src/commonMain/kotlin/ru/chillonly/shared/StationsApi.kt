package ru.chillonly.shared

import io.ktor.client.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.*
import io.ktor.client.features.logging.Logger
import io.ktor.http.*

class StationsApi {

    private val baseUrl = "https://bazhenovalexei.pythonanywhere.com/api/"

    private val client = HttpClient {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(JsonFeature)
        install(Auth) {
            basic {
                username = "mobile"
                password = "passmob123"
            }
        }
    }

    suspend fun getStations(): List<Station> =
        client.get<MutableList<Station>>("$baseUrl/stations/")

}
