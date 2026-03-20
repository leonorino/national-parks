package me.leonorino.nationalparks.ui.passport.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.leonorino.nationalparks.R
import me.leonorino.nationalparks.ui.theme.DarkText
import me.leonorino.nationalparks.ui.theme.ForestGreen
import me.leonorino.nationalparks.ui.theme.ParkGreen
import me.leonorino.nationalparks.ui.theme.ParkGreenLight

@Composable
fun PassportHeader(
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            color = ParkGreen
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = onSettingsClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.White.copy(alpha = 0.5f)
            ),
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(ParkGreenLight.copy(alpha = 0.5f))
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = DarkText,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
