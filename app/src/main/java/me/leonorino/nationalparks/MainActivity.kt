package me.leonorino.nationalparks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import me.leonorino.nationalparks.ui.navigation.NationalParksBottomBar
import me.leonorino.nationalparks.ui.navigation.NationalParksNavHost
import me.leonorino.nationalparks.ui.theme.NationalParksTheme

class MainActivity : AppCompatActivity() {
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
