package commonMain.kotlin.ru.chillonly.shared.network

import io.ktor.client.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import commonMain.kotlin.ru.chillonly.shared.network.response.Station

class StationsApi {

    private val baseUrl = "https://bazhenovalexei.pythonanywhere.com/api"

    private val client by lazy {
        HttpClient {
            install(JsonFeature)
            install(Auth) {
                basic {
                    username = "mobile"
                    password = "passmob123"
                    sendWithoutRequest = true
                }
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }

    suspend fun getStations(): List<Station> =
        client.get<MutableList<Station>>("$baseUrl/stations/")

}
