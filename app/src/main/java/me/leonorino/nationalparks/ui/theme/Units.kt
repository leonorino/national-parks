package me.leonorino.nationalparks.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class UnitSystem {
    METRIC, IMPERIAL
}

class UnitState(initial: UnitSystem = UnitSystem.METRIC) {
    var currentUnit by mutableStateOf(initial)

    fun toggle() {
        currentUnit = if (currentUnit == UnitSystem.METRIC)
            UnitSystem.IMPERIAL else UnitSystem.METRIC
    }
}

val LocalUnitSystem = compositionLocalOf<UnitState> {
    error("No UnitState provided")
}
