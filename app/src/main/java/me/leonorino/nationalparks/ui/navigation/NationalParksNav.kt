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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import me.leonorino.nationalparks.NationalParksApplication
import me.leonorino.nationalparks.ui.details.DetailsScreen
import me.leonorino.nationalparks.ui.details.DetailsViewModel
import me.leonorino.nationalparks.ui.explore.ExploreScreen
import me.leonorino.nationalparks.ui.explore.ExploreViewModel
import me.leonorino.nationalparks.ui.passport.PassportScreen
import me.leonorino.nationalparks.ui.passport.PassportViewModel
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
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
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

    NavHost(
        navController = navController,
        startDestination = Screen.Passport.route,
        modifier = modifier
    ) {
        composable(Screen.Passport.route) {
            PassportScreen(
                viewModel = passportViewModel,
                onParkClick = { parkId ->
                    navController.navigate(Screen.Details.createRoute(parkId))
                }
            )
        }
        composable(Screen.Explore.route) { ExploreScreen(exploreViewModel, onParkClick = { parkId ->
            navController.navigate(Screen.Details.createRoute(parkId))
        }) }
        composable(Screen.Map.route) { PlaceholderScreen("Map") }

        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("parkId") { type = NavType.StringType })
        ) { backStackEntry ->
            val parkId = backStackEntry.arguments?.getString("parkId") ?: ""
            val detailsViewModel: DetailsViewModel = viewModel(factory = DetailsViewModel.Factory)

            DetailsScreen(
                parkId = parkId,
                viewModel = detailsViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun PlaceholderScreen(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text)
    }
}
