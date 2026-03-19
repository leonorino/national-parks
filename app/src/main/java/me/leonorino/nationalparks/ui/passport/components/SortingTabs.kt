package me.leonorino.nationalparks.ui.passport

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.leonorino.nationalparks.R
import me.leonorino.nationalparks.ui.theme.MutedText
import me.leonorino.nationalparks.ui.theme.ParkGreen

@Composable
fun SortingTabs(
    currentSort: SortOrder,
    onSortChanged: (SortOrder) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.stamps_collected),
            style = MaterialTheme.typography.labelLarge,
            color = MutedText,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.weight(1f)
        )

        SortTab(
            text = stringResource(R.string.sort_recent),
            isSelected = currentSort == SortOrder.RECENT,
            onClick = { onSortChanged(SortOrder.RECENT) }
        )
        Spacer(modifier = Modifier.width(12.dp))
        SortTab(
            text = stringResource(R.string.sort_states),
            isSelected = currentSort == SortOrder.STATE,
            onClick = { onSortChanged(SortOrder.STATE) }
        )
        Spacer(modifier = Modifier.width(12.dp))
        SortTab(
            text = stringResource(R.string.sort_alphabetical),
            isSelected = currentSort == SortOrder.ALPHABETICAL,
            onClick = { onSortChanged(SortOrder.ALPHABETICAL) }
        )
    }
}

@Composable
private fun SortTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier.clickable { onClick() },
        color = if (isSelected) ParkGreen else MutedText.copy(alpha = 0.6f),
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        style = MaterialTheme.typography.bodyMedium
    )
}
