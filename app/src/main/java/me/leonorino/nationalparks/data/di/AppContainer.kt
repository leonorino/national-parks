package me.leonorino.nationalparks.data.di

import android.content.Context
import me.leonorino.nationalparks.data.repository.ParkRepository

class AppContainer(private val context: Context) {
    val repository: ParkRepository by lazy {
        ParkRepository()
    }
}
