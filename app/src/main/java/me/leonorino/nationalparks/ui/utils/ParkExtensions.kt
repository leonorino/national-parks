package me.leonorino.nationalparks.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.leonorino.nationalparks.R
import me.leonorino.nationalparks.model.Park
import me.leonorino.nationalparks.ui.theme.LocalUnitSystem
import me.leonorino.nationalparks.ui.theme.UnitSystem
import java.text.DecimalFormat

private val DECIMAL_FORMAT = DecimalFormat("#,###")

val Park.fullName: String
    @Composable
    get() = stringResource(id = nameResId)

val Park.description: String
    @Composable
    get() = stringResource(id = descriptionResId)

val Park.categories: List<String>
    @Composable
    get() = categoryResIds.map { stringResource(id = it) }

val Park.formattedElevation: String
    @Composable
    get() {
        val isMetric = isMetric()

        val value = if (isMetric) elevationMeters else elevationFeet
        val stringId = if (isMetric) R.string.elevationMeters else R.string.elevationFeet
        val formattedValue = DECIMAL_FORMAT.format(value)
        return stringResource(stringId, formattedValue)
    }

val Park.formattedArea: String
    @Composable
    get() {
        val isMetric = isMetric()

        val value = if (isMetric) areaSqKm else areaSqMiles
        val stringId = if (isMetric) R.string.areaMeters else R.string.areaMiles
        val formattedValue = DECIMAL_FORMAT.format(value)
        return stringResource(stringId, formattedValue)
    }

@Composable
private fun isMetric(): Boolean {
    val unitState = LocalUnitSystem.current
    return unitState.currentDistanceUnit == UnitSystem.METRIC
}
