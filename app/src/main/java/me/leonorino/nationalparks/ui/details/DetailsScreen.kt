package me.leonorino.nationalparks.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.leonorino.nationalparks.R
import me.leonorino.nationalparks.model.Park
import me.leonorino.nationalparks.model.USState
import me.leonorino.nationalparks.ui.details.components.InfoCard
import me.leonorino.nationalparks.ui.theme.BeigeBackground
import me.leonorino.nationalparks.ui.theme.LocalUnitSystem
import me.leonorino.nationalparks.ui.theme.MutedText
import me.leonorino.nationalparks.ui.theme.NationalParksTheme
import me.leonorino.nationalparks.ui.theme.UnitSystem
import me.leonorino.nationalparks.ui.utils.description
import me.leonorino.nationalparks.ui.utils.formattedArea
import me.leonorino.nationalparks.ui.utils.formattedElevation
import me.leonorino.nationalparks.ui.utils.fullName

@Composable
fun DetailsScreen(parkId: String, viewModel: DetailsViewModel, onBack: () -> Unit) {
    var park by remember { mutableStateOf<Park?>(null) }

    LaunchedEffect(parkId) {
        park = viewModel.getPark(parkId)
    }

    park?.let { currentPark ->
        DetailsContent(park = currentPark, onBack = onBack)
    }
}

@Composable
fun DetailsContent(park: Park, onBack: () -> Unit) {
    val unitState = LocalUnitSystem.current
    val isMetric = unitState.currentUnit == UnitSystem.METRIC

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BeigeBackground)
    ) {
        Box(modifier = Modifier.height(350.dp).fillMaxWidth()) {
            AsyncImage(
                model = "file:///android_asset/${park.expandedImageUrl}",
                placeholder = painterResource(R.drawable.card_preview),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            IconButton(onClick = onBack, modifier = Modifier.padding(16.dp)) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back", tint = Color.White)
            }
        }

        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = park.fullName, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(text = park.states.first().fullName, color = MutedText)

            Row(modifier = Modifier.padding(vertical = 24.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoCard(
                    label = stringResource(R.string.elevation),
                    value = park.formattedElevation,
                    icon = Icons.Default.Terrain,
                    modifier = Modifier.weight(1f)
                )
                InfoCard(
                    label = stringResource(R.string.visitors),
                    value = park.yearlyVisitors.toString(),
                    icon = Icons.Default.Groups,
                    modifier = Modifier.weight(1f)
                )
                InfoCard(
                    label = stringResource(R.string.area),
                    value = park.formattedArea,
                    icon = Icons.Default.Map,
                    modifier = Modifier.weight(1f)
                )
            }

            Text(text = stringResource(R.string.aboutPark), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(text = park.description, modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Preview(showBackground = true, locale = "en")
@Composable
fun DetailsScreenPreview() {
    val mockPark = Park(
        id = "yosemite",
        nameResId = R.string.park_yosemite_name,
        descriptionResId = R.string.park_yosemite_description,
        categoryResIds = listOf(R.string.category_mountains, R.string.category_waterfalls),
        states = listOf(USState.CA),
        cardImageUrl = "images/yosemite_card.jpg",
        expandedImageUrl = "images/yosemite_details.jpg",
        elevationMeters = 1219,
        areaSqKm = 3027.0,
        yearlyVisitors = 3900000
    )

    NationalParksTheme {
        DetailsContent(
            park = mockPark,
            onBack = {}
        )
    }
}
