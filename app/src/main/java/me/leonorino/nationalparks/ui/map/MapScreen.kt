package me.leonorino.nationalparks.ui.map

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import me.leonorino.nationalparks.data.local.ParkData
import me.leonorino.nationalparks.ui.utils.Constants
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    initialLat: Double? = null,
    initialLon: Double? = null,
    initialZoom: Double? = null,
    onParkClick: (String) -> Unit
) {
    val context = LocalContext.current

    Configuration.getInstance().load(
        context,
        context.getSharedPreferences(Constants.OSM_PREFS, Context.MODE_PRIVATE)
    )

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { ctx ->
            MapView(ctx).apply {
                setMultiTouchControls(true)
                
                val centerLat = initialLat ?: 39.8283
                val centerLon = initialLon ?: -98.5795
                val zoom = initialZoom ?: 4.0
                
                controller.setZoom(zoom)
                controller.setCenter(GeoPoint(centerLat, centerLon))

                ParkData.parks.forEach { park ->
                    val marker = Marker(this)
                    marker.position = GeoPoint(park.latitude, park.longitude)

                    marker.setOnMarkerClickListener { _, _ ->
                        onParkClick(park.id)
                        true
                    }

                    this.overlays.add(marker)
                }
            }
        },
    )
}