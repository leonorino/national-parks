package me.leonorino.nationalparks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import me.leonorino.nationalparks.ui.navigation.NationalParksBottomBar
import me.leonorino.nationalparks.ui.navigation.NationalParksNavHost
import me.leonorino.nationalparks.ui.theme.NationalParksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NationalParksTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { NationalParksBottomBar(navController) }
                ) { innerPadding ->
                    NationalParksNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
