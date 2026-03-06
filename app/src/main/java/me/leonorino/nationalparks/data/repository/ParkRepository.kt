package me.leonorino.nationalparks.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import me.leonorino.nationalparks.data.local.ParkDao
import me.leonorino.nationalparks.model.Park

class ParkRepository(
    private val parkDao: ParkDao,
    private val context: Context
) {
    val allParks: Flow<List<Park>> = parkDao.getAllParks()

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    suspend fun initializeDatabaseIfNeeded() {
        try {
            val jsonString = context.assets.open("parks.json").bufferedReader().use { it.readText() }
            val parks = json.decodeFromString<List<Park>>(jsonString)
            parkDao.insertParks(parks)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}