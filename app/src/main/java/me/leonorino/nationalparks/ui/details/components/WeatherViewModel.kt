package me.leonorino.nationalparks.ui.details.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.leonorino.nationalparks.NationalParksApplication
import me.leonorino.nationalparks.data.repository.WeatherRepository
import me.leonorino.nationalparks.ui.theme.TempSystem

sealed interface WeatherUiState {
    object Loading : WeatherUiState
    data class Success(
        val temperature: Double,
        val condition: String,
        val icon: ImageVector
    ) : WeatherUiState
    object Error : WeatherUiState
}

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun fetchWeather(lat: Double, lon: Double, unit: TempSystem) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            try {
                val unitParam = if (unit == TempSystem.CELSIUS) "celsius" else "fahrenheit"
                val response = repository.getWeather(lat, lon, unitParam)
                _uiState.value = WeatherUiState.Success(
                    temperature = response.temperature,
                    condition = mapWeatherCodeToString(response.weatherCode),
                    icon = mapWeatherCodeToIcon(response.weatherCode)
                )
            } catch (_: Exception) {
                _uiState.value = WeatherUiState.Error
            }
        }
    }

    private fun mapWeatherCodeToString(code: Int): String {
        return when (code) {
            0 -> "Clear sky"
            1, 2, 3 -> "Mainly clear, partly cloudy, and overcast"
            45, 48 -> "Fog and depositing rime fog"
            51, 53, 55 -> "Drizzle: Light, moderate, and dense intensity"
            56, 57 -> "Freezing Drizzle: Light and dense intensity"
            61, 63, 65 -> "Rain: Slight, moderate and heavy intensity"
            66, 67 -> "Freezing Rain: Light and heavy intensity"
            71, 73, 75 -> "Snow fall: Slight, moderate, and heavy intensity"
            77 -> "Snow grains"
            80, 81, 82 -> "Rain showers: Slight, moderate, and violent"
            85, 86 -> "Snow showers slight and heavy"
            95 -> "Thunderstorm: Slight or moderate"
            96, 99 -> "Thunderstorm with slight and heavy hail"
            else -> "Unknown"
        }
    }

    private fun mapWeatherCodeToIcon(code: Int): ImageVector {
        return when (code) {
            0, 1 -> Icons.Default.WbSunny
            2, 3 -> Icons.Default.CloudQueue
            45, 48 -> Icons.Default.Cloud
            51, 53, 55, 61, 63, 65, 80, 81, 82 -> Icons.Default.Grain
            71, 73, 75, 77, 85, 86 -> Icons.Default.AcUnit
            95, 96, 99 -> Icons.Default.Thunderstorm
            else -> Icons.Default.Cloud
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NationalParksApplication)
                WeatherViewModel(repository = application.container.weatherRepository)
            }
        }
    }
}
