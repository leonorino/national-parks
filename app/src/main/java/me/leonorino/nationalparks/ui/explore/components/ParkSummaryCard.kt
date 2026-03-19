package me.leonorino.nationalparks.ui.explore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.leonorino.nationalparks.R
import me.leonorino.nationalparks.model.Park
import me.leonorino.nationalparks.model.USState
import me.leonorino.nationalparks.ui.theme.DarkText
import me.leonorino.nationalparks.ui.theme.MutedText
import me.leonorino.nationalparks.ui.utils.fullName

@Composable
fun ParkSummaryCard(park: Park, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = "file:///android_asset/${park.cardImageUrl}",
                placeholder = painterResource(R.drawable.card_preview),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            0.0f to Color.White,
                            0.45f to Color.White,
                            0.75f to Color.Transparent
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.45f)
                    .padding(start = 24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = park.fullName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
                Text(
                    text = park.states.first().fullName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MutedText
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ParkSummaryCardPreview() {
    val mockPark = Park(
        id = "yellowstone",
        nameResId = R.string.park_yellowstone_name,
        descriptionResId = R.string.park_yellowstone_description,
        categoryResIds = listOf(R.string.category_popular),
        states = listOf(USState.CA),
        cardImageUrl = "images/yellowstone_card.jpg",
        expandedImageUrl = "images/yellowstone_details.jpg",
        latitude = 44.4280,
        longitude = -110.5885,
        badgeImageUrl = "badges/yellowstone.webp",
        elevationMeters = 1000,
        areaSqKm = 500.0,
        yearlyVisitors = 1000
    )

    MaterialTheme {
        ParkSummaryCard(
            park = mockPark,
            onClick = {}
        )
    }
}
