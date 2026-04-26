package me.leonorino.nationalparks.data.di

import android.content.Context
import kotlinx.serialization.json.Json
import me.leonorino.nationalparks.data.local.NationalParksDatabase
import me.leonorino.nationalparks.data.remote.WeatherApiService
import me.leonorino.nationalparks.data.repository.ParkRepository
import me.leonorino.nationalparks.data.repository.WeatherRepository
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class AppContainer(private val context: Context) {
    private val baseUrl = "https://api.open-meteo.com/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val weatherApiService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }

    private val database: NationalParksDatabase by lazy {
        NationalParksDatabase.getDatabase(context)
    }

    val repository: ParkRepository by lazy {
        ParkRepository(database.visitDao())
    }

    val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(weatherApiService)
    }
}
