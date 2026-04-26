package me.leonorino.nationalparks.data.remote

import me.leonorino.nationalparks.data.remote.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current_weather") currentWeather: Boolean = true,
        @Query("temperature_unit") temperatureUnit: String = "celsius"
    ): WeatherResponse
}
