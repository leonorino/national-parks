package me.leonorino.nationalparks.data.di

import android.content.Context
import androidx.room.Room
import me.leonorino.nationalparks.data.local.ParkDatabase
import me.leonorino.nationalparks.data.repository.ParkRepository

class AppContainer(private val context: Context) {

    private val database: ParkDatabase by lazy {
        Room.databaseBuilder(
            context,
            ParkDatabase::class.java,
            "national_parks.db"
        ).build()
    }

    val repository: ParkRepository by lazy {
        ParkRepository(database.parkDao(), context)
    }
}