package me.leonorino.nationalparks.data.di

import android.content.Context
import me.leonorino.nationalparks.data.local.NationalParksDatabase
import me.leonorino.nationalparks.data.repository.ParkRepository

class AppContainer(private val context: Context) {
    private val database: NationalParksDatabase by lazy {
        NationalParksDatabase.getDatabase(context)
    }

    val repository: ParkRepository by lazy {
        ParkRepository(database.visitDao())
    }
}
