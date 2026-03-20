package me.leonorino.nationalparks.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.leonorino.nationalparks.R
import me.leonorino.nationalparks.ui.settings.components.LanguageDialog
import me.leonorino.nationalparks.ui.settings.components.SettingsCard
import me.leonorino.nationalparks.ui.settings.components.SettingsItem
import me.leonorino.nationalparks.ui.settings.components.SettingsSectionHeader
import me.leonorino.nationalparks.ui.settings.components.UnitToggle
import me.leonorino.nationalparks.ui.theme.BeigeBackground
import me.leonorino.nationalparks.ui.theme.LocalUnitSystem
import me.leonorino.nationalparks.ui.theme.ParkGreen
import me.leonorino.nationalparks.ui.theme.TempSystem
import me.leonorino.nationalparks.ui.theme.UnitSystem
import me.leonorino.nationalparks.ui.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    val unitState = LocalUnitSystem.current
    var showLanguageDialog by remember { mutableStateOf(false) }

    if (showLanguageDialog) {
        LanguageDialog(
            currentLanguageCode = unitState.currentLanguage,
            onLanguageSelected = { code ->
                unitState.setLanguage(code)
                showLanguageDialog = false
            },
            onDismiss = { showLanguageDialog = false }
        )
    }

    Scaffold(
        containerColor = BeigeBackground,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back), tint = ParkGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            SettingsSectionHeader(stringResource(R.string.general))
            
            SettingsCard {
                SettingsItem(
                    icon = Icons.Default.Language,
                    title = stringResource(R.string.app_language),
                    subtitle = if (unitState.currentLanguage == Constants.LANG_RU) {
                        stringResource(R.string.language_russian)
                    } else {
                        stringResource(R.string.language_english)
                    },
                    iconColor = ParkGreen,
                    onClick = { showLanguageDialog = true }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            SettingsSectionHeader(stringResource(R.string.units))

            SettingsCard {
                Column {
                    SettingsItem(
                        icon = Icons.Default.Straighten,
                        title = stringResource(R.string.distance_units),
                        iconColor = ParkGreen
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))

                    UnitToggle(
                        selectedOption = if (unitState.currentDistanceUnit == UnitSystem.METRIC) 0 else 1,
                        options = listOf(stringResource(R.string.metric), stringResource(R.string.imperial)),
                        onOptionSelected = { index ->
                            unitState.setDistanceUnit(if (index == 0) UnitSystem.METRIC else UnitSystem.IMPERIAL)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            SettingsCard {
                Column {
                    SettingsItem(
                        icon = Icons.Default.Thermostat,
                        title = stringResource(R.string.temperature),
                        iconColor = ParkGreen
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))

                    UnitToggle(
                        selectedOption = if (unitState.currentTempUnit == TempSystem.CELSIUS) 0 else 1,
                        options = listOf(stringResource(R.string.celsius), stringResource(R.string.fahrenheit)),
                        onOptionSelected = { index ->
                            unitState.setTempUnit(if (index == 0) TempSystem.CELSIUS else TempSystem.FAHRENHEIT)
                        }
                    )
                }
            }
        }
    }
}
