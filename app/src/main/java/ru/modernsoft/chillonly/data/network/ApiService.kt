package ru.modernsoft.chillonly.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.modernsoft.chillonly.BuildConfig

object ApiService {

    private var api: ApiEndpoints? = null

    fun getApi(): ApiEndpoints {
        return if (api == null)
            createApi()
        else
            api as ApiEndpoints
    }

    private fun createApi(): ApiEndpoints {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor("mobile", "passmob123"))
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiEndpoints::class.java)
        return api as ApiEndpoints
    }
}
