package me.leonorino.nationalparks.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("current_weather")
    val currentWeather: CurrentWeatherDto
)

@Serializable
data class CurrentWeatherDto(
    val temperature: Double,
    @SerialName("weathercode")
    val weatherCode: Int,
    @SerialName("is_day")
    val isDay: Int,
    val time: String
)
