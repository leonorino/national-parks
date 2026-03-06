package me.leonorino.nationalparks.ui.explore.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.leonorino.nationalparks.R
import me.leonorino.nationalparks.ui.theme.MutedText

@Composable
fun ParkSearchBar(modifier: Modifier = Modifier) {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text(stringResource(R.string.search_placeholder), color = MutedText) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MutedText) },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(30.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}