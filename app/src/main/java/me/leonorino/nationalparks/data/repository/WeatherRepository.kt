package me.leonorino.nationalparks.data.repository

import me.leonorino.nationalparks.data.remote.WeatherApiService
import me.leonorino.nationalparks.data.remote.model.CurrentWeatherDto

class WeatherRepository(private val apiService: WeatherApiService) {
    suspend fun getWeather(lat: Double, lon: Double, unit: String): CurrentWeatherDto {
        return apiService.getWeather(
            latitude = lat,
            longitude = lon,
            temperatureUnit = unit
        ).currentWeather
    }
}
