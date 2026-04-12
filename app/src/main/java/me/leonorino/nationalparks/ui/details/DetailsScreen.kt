package me.leonorino.nationalparks.ui.details

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import me.leonorino.nationalparks.R
import me.leonorino.nationalparks.model.Park
import me.leonorino.nationalparks.model.USState
import me.leonorino.nationalparks.ui.details.components.InfoCard
import me.leonorino.nationalparks.ui.theme.BeigeBackground
import me.leonorino.nationalparks.ui.theme.DarkText
import me.leonorino.nationalparks.ui.theme.ForestGreen
import me.leonorino.nationalparks.ui.theme.MutedText
import me.leonorino.nationalparks.ui.theme.NationalParksTheme
import me.leonorino.nationalparks.ui.theme.ParkGreen
import me.leonorino.nationalparks.ui.utils.Constants
import me.leonorino.nationalparks.ui.utils.description
import me.leonorino.nationalparks.ui.utils.formattedArea
import me.leonorino.nationalparks.ui.utils.formattedElevation
import me.leonorino.nationalparks.ui.utils.fullName
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.util.Calendar

@Composable
fun DetailsScreen(
    parkId: String,
    viewModel: DetailsViewModel,
    onBack: () -> Unit,
    onShowMap: (Park) -> Unit = {}
) {
    var park by remember { mutableStateOf<Park?>(null) }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(parkId) {
        park = viewModel.getPark(parkId)
        viewModel.loadVisit(parkId)
    }

    park?.let { currentPark ->
        DetailsContent(
            park = currentPark,
            uiState = uiState,
            onBack = onBack,
            onAddVisit = viewModel::showCreateEditor,
            onEditVisit = viewModel::showEditEditor,
            onDeleteVisit = viewModel::showDeleteDialog,
            onShowMap = { onShowMap(currentPark) }
        )

        if (uiState.isEditorVisible) {
            VisitEditorDialog(
                uiState = uiState,
                onDismiss = viewModel::dismissEditor,
                onDateChange = viewModel::updateVisitedDate,
                onNotesChange = viewModel::updateNotesInput,
                onRatingChange = viewModel::updateRatingInput,
                onSave = { viewModel.saveVisit(currentPark.id) }
            )
        }

        if (uiState.isDeleteDialogVisible) {
            DeleteVisitDialog(
                onDismiss = viewModel::dismissDeleteDialog,
                onConfirm = { viewModel.deleteVisit(currentPark.id) }
            )
        }
    }
}

@Composable
fun DetailsContent(
    park: Park,
    uiState: DetailsUiState,
    onBack: () -> Unit,
    onAddVisit: () -> Unit,
    onEditVisit: () -> Unit,
    onDeleteVisit: () -> Unit,
    onShowMap: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BeigeBackground)
    ) {
        Box(modifier = Modifier.height(350.dp).fillMaxWidth()) {
            AsyncImage(
                model = "${Constants.ASSET_PATH}${park.expandedImageUrl}",
                placeholder = painterResource(R.drawable.card_preview),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            
            // Header buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.3f))
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = stringResource(R.string.back),
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = park.fullName, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(text = stringResource(park.states.first().fullNameResId), color = MutedText)

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

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (uiState.visit != null) {
                    VisitSummaryCard(visit = uiState.visit)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Surface(
                    onClick = if (uiState.isVisited) onEditVisit else onAddVisit,
                    shape = RoundedCornerShape(16.dp),
                    color = ForestGreen,
                    contentColor = Color.White,
                    modifier = Modifier.fillMaxWidth().height(64.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = if (uiState.isVisited) Icons.Default.Edit else Icons.Default.Verified,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (uiState.isVisited) stringResource(R.string.edit_visit) else stringResource(R.string.add_visit),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                }

                if (uiState.isVisited) {
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = onDeleteVisit,
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = stringResource(R.string.delete_visit), fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (uiState.isVisited) {
                        stringResource(R.string.visit_saved_info)
                    } else {
                        stringResource(R.string.stamp_info)
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MutedText
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            MapSection(
                latitude = park.latitude,
                longitude = park.longitude,
                onMapClick = onShowMap
            )
        }
    }
}

@Composable
private fun VisitSummaryCard(
    visit: me.leonorino.nationalparks.model.Visit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.visit_details),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(
                    R.string.visited_on,
                    VisitDateFormatter.formatForDisplay(visit.visitedDate)
                ),
                color = DarkText
            )
            if (visit.rating != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = ParkGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(text = stringResource(R.string.rating_value, visit.rating))
                }
            }
            if (visit.notes.isNotBlank()) {
                Text(text = visit.notes, color = MutedText)
            }
        }
    }
}

@Composable
private fun VisitEditorDialog(
    uiState: DetailsUiState,
    onDismiss: () -> Unit,
    onDateChange: (Long) -> Unit,
    onNotesChange: (String) -> Unit,
    onRatingChange: (String) -> Unit,
    onSave: () -> Unit
) {
    val context = LocalContext.current
    val datePickerDialog = remember(context, uiState.visitedDate) {
        val initialDate = Calendar.getInstance().apply {
            timeInMillis = uiState.visitedDate
        }
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    set(Calendar.HOUR_OF_DAY, 12)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                onDateChange(selectedDate.timeInMillis)
            },
            initialDate.get(Calendar.YEAR),
            initialDate.get(Calendar.MONTH),
            initialDate.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.maxDate = System.currentTimeMillis()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(
                    if (uiState.isVisited) R.string.edit_visit else R.string.add_visit
                )
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = VisitDateFormatter.formatForDisplay(uiState.visitedDate),
                    onValueChange = {},
                    label = { Text(stringResource(R.string.visit_date_label)) },
                    readOnly = true,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerDialog.show() }
                )

                OutlinedTextField(
                    value = uiState.ratingInput,
                    onValueChange = onRatingChange,
                    label = { Text(stringResource(R.string.rating_label)) },
                    placeholder = { Text(stringResource(R.string.rating_placeholder)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.ratingError,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                if (uiState.ratingError) {
                    Text(
                        text = stringResource(R.string.invalid_rating_message),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                OutlinedTextField(
                    value = uiState.notesInput,
                    onValueChange = onNotesChange,
                    label = { Text(stringResource(R.string.notes_label)) },
                    placeholder = { Text(stringResource(R.string.notes_placeholder)) },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            OutlinedButton(onClick = onSave) {
                Text(text = stringResource(R.string.save_visit))
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
private fun DeleteVisitDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.delete_visit)) },
        text = { Text(text = stringResource(R.string.delete_visit_confirmation)) },
        confirmButton = {
            OutlinedButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.delete_visit))
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
fun MapSection(
    latitude: Double,
    longitude: Double,
    modifier: Modifier = Modifier,
    onMapClick: () -> Unit = {}
) {
    val context = LocalContext.current

    // Initialize OSM Configuration
    LaunchedEffect(Unit) {
        Configuration.getInstance().load(
            context,
            context.getSharedPreferences(Constants.OSM_PREFS, Context.MODE_PRIVATE)
        )
        Configuration.getInstance().userAgentValue = context.packageName
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable { onMapClick() }
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                MapView(ctx).apply {
                    setMultiTouchControls(false)
                    controller.setZoom(10.0)
                    val startPoint = GeoPoint(latitude, longitude)
                    controller.setCenter(startPoint)

                    val marker = Marker(this)
                    marker.position = startPoint
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    overlays.add(marker)
                }
            },
            update = { view ->
                view.controller.setCenter(GeoPoint(latitude, longitude))
            }
        )

        Surface(
            onClick = onMapClick,
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.interactive_map),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
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
        latitude = 37.8651,
        longitude = -119.5383,
        badgeImageUrl = "badges/yosemite.webp",
        elevationMeters = 1219,
        areaSqKm = 3027.0,
        yearlyVisitors = 3900000
    )

    NationalParksTheme {
        DetailsContent(
            park = mockPark,
            uiState = DetailsUiState(),
            onBack = {},
            onAddVisit = {},
            onEditVisit = {},
            onDeleteVisit = {},
            onShowMap = {}
        )
    }
}
