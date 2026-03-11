package me.leonorino.nationalparks.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import me.leonorino.nationalparks.data.local.ParkData
import me.leonorino.nationalparks.model.Park

class ParkRepository {
    val allParks: Flow<List<Park>> = flowOf(ParkData.parks)

    suspend fun getParkById(parkId: String): Park? {
        return ParkData.parks.find { it.id == parkId }
    }
}
