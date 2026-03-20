package me.leonorino.nationalparks.ui.theme

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.LocaleListCompat
import me.leonorino.nationalparks.ui.utils.Constants

enum class UnitSystem {
    METRIC, IMPERIAL
}

enum class TempSystem {
    CELSIUS, FAHRENHEIT
}

class UnitState(
    initialDistance: UnitSystem = UnitSystem.METRIC,
    initialTemp: TempSystem = TempSystem.CELSIUS
) {
    var currentDistanceUnit by mutableStateOf(initialDistance)
    var currentTempUnit by mutableStateOf(initialTemp)

    var currentLanguage by mutableStateOf(
        AppCompatDelegate.getApplicationLocales().get(0)?.language ?: Constants.DEFAULT_LANGUAGE
    )

    fun setDistanceUnit(unit: UnitSystem) {
        currentDistanceUnit = unit
    }

    fun setTempUnit(unit: TempSystem) {
        currentTempUnit = unit
    }

    fun setLanguage(languageCode: String) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
        currentLanguage = languageCode
    }
}

val LocalUnitSystem = compositionLocalOf<UnitState> {
    error("No UnitState provided")
}
