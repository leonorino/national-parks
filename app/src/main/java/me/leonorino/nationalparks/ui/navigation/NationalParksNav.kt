package me.leonorino.nationalparks.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import me.leonorino.nationalparks.ui.details.DetailsScreen
import me.leonorino.nationalparks.ui.details.DetailsViewModel
import me.leonorino.nationalparks.ui.explore.ExploreScreen
import me.leonorino.nationalparks.ui.explore.ExploreViewModel
import me.leonorino.nationalparks.ui.map.MapScreen
import me.leonorino.nationalparks.ui.passport.PassportScreen
import me.leonorino.nationalparks.ui.passport.PassportViewModel
import me.leonorino.nationalparks.ui.settings.SettingsScreen
import me.leonorino.nationalparks.ui.theme.ParkGreen

@Composable
fun NationalParksBottomBar(navController: NavController) {
    val items = listOf(Screen.Passport, Screen.Explore, Screen.Map)

    NavigationBar(containerColor = Color.White) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(imageVector = screen.icon!!, contentDescription = null) },
                label = { Text(text = stringResource(id = screen.labelResId!!)) },
                selected = currentRoute?.startsWith(screen.route) == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(Graph.HOME) {
                            saveState = true
                            inclusive = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ParkGreen,
                    selectedTextColor = ParkGreen,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun NationalParksNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    val exploreViewModel: ExploreViewModel = viewModel(factory = ExploreViewModel.Factory)
    val passportViewModel: PassportViewModel = viewModel(factory = PassportViewModel.Factory)
    val onParkClick = { parkId: String ->
        navController.navigate(Screen.Details.createRoute(parkId))
    }

    NavHost(
        navController = navController,
        startDestination = Graph.HOME,
        route = Graph.ROOT,
        modifier = modifier
    ) {
        navigation(
            startDestination = Screen.Passport.route,
            route = Graph.HOME
        ) {
            composable(Screen.Passport.route) {
                PassportScreen(
                    viewModel = passportViewModel,
                    onParkClick = { parkId ->
                        navController.navigate(Screen.Details.createRoute(parkId))
                    },
                    onSettingsClick = {
                        navController.navigate(Screen.Settings.route)
                    }
                )
            }
            composable(Screen.Explore.route) { ExploreScreen(exploreViewModel, onParkClick = onParkClick) }
            composable(
                route = Screen.Map.navRoute,
                arguments = listOf(
                    navArgument("lat") { type = NavType.StringType; nullable = true },
                    navArgument("lon") { type = NavType.StringType; nullable = true },
                    navArgument("zoom") { type = NavType.StringType; nullable = true }
                )
            ) { backStackEntry ->
                val lat = backStackEntry.arguments?.getString("lat")?.toDoubleOrNull()
                val lon = backStackEntry.arguments?.getString("lon")?.toDoubleOrNull()
                val zoom = backStackEntry.arguments?.getString("zoom")?.toDoubleOrNull()
                MapScreen(
                    onParkClick = onParkClick,
                    initialLat = lat,
                    initialLon = lon,
                    initialZoom = zoom
                )
            }

            composable(
                route = Screen.Details.route,
                arguments = listOf(navArgument("parkId") { type = NavType.StringType })
            ) { backStackEntry ->
                val parkId = backStackEntry.arguments?.getString("parkId") ?: ""
                val detailsViewModel: DetailsViewModel = viewModel(factory = DetailsViewModel.Factory)

                DetailsScreen(
                    parkId = parkId,
                    viewModel = detailsViewModel,
                    onBack = { navController.popBackStack() },
                    onShowMap = { park ->
                        val route = Screen.Map.createRoute(park.latitude, park.longitude, 12.0)
                        navController.navigate(route) {
                            popUpTo(Graph.HOME) {
                                saveState = true
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            composable(Screen.Settings.route) {
                SettingsScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
