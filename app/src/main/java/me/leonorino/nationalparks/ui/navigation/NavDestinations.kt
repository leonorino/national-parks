package me.leonorino.nationalparks.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.ImageVector
import me.leonorino.nationalparks.R

sealed class Screen(val route: String, val labelResId: Int? = null, val icon: ImageVector? = null) {
    object Passport : Screen("passport", R.string.nav_passport, Icons.Default.LocalActivity)
    object Explore : Screen("explore", R.string.nav_explore, Icons.Default.Explore)
    object Map : Screen("map", R.string.nav_map, Icons.Default.Map)
    object Details : Screen("details/{parkId}") {
        fun createRoute(parkId: String) = "details/$parkId"
    }
}