package me.leonorino.nationalparks.ui.passport

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.leonorino.nationalparks.ui.passport.components.PassportHeader
import me.leonorino.nationalparks.ui.passport.components.ProgressCard
import me.leonorino.nationalparks.ui.passport.components.SortingTabs
import me.leonorino.nationalparks.ui.passport.components.StampItem

@Composable
fun PassportScreen(
    viewModel: PassportViewModel,
    onParkClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            PassportHeader()
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            ProgressCard(
                visitedParks = uiState.visitedParks,
                totalParks = uiState.totalParks,
                progressPercentage = uiState.progressPercentage
            )
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            SortingTabs(
                currentSort = uiState.currentSort,
                onSortChanged = { viewModel.onSortChanged(it) }
            )
        }

        items(
            items = uiState.parksWithStatus,
            key = { it.park.id }
        ) { parkWithStatus ->
            StampItem(
                parkWithStatus = parkWithStatus,
                onClick = { onParkClick(parkWithStatus.park.id) }
            )
        }
    }
}
