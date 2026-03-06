package me.leonorino.nationalparks.ui.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.leonorino.nationalparks.R
import me.leonorino.nationalparks.ui.explore.components.ParkSearchBar
import me.leonorino.nationalparks.ui.explore.components.ParkSummaryCard
import me.leonorino.nationalparks.ui.theme.BeigeBackground
import me.leonorino.nationalparks.ui.theme.ParkGreen

@Composable
fun ExploreScreen(viewModel: ExploreViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BeigeBackground)
    ) {
        Text(
            text = stringResource(R.string.explore_heading),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 24.dp, bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        ParkSearchBar()

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            val categories = listOf("All parks", "Forests", "Plains", "Deserts")
            items(categories) { category ->
                FilterChip(
                    selected = category == "All parks",
                    onClick = { },
                    label = { Text(category) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ParkGreen,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when (val state = uiState) {
                is ExploreUiState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                is ExploreUiState.Error -> Text(stringResource(state.messageResId), Modifier.align(Alignment.Center))
                is ExploreUiState.Success -> {
                    LazyColumn {
                        items(state.parks) { park ->
                            ParkSummaryCard(park = park, onClick = { })
                        }
                    }
                }
            }
        }
    }
}