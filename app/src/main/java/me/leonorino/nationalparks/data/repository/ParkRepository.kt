package me.leonorino.nationalparks.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import me.leonorino.nationalparks.data.local.ParkData
import me.leonorino.nationalparks.data.local.VisitDao
import me.leonorino.nationalparks.model.Park
import me.leonorino.nationalparks.model.ParkWithStatus
import me.leonorino.nationalparks.model.Visit

class ParkRepository(
    private val visitDao: VisitDao
) {
    val allParks: Flow<List<Park>> = flowOf(ParkData.parks)
    val allParksWithStatus: Flow<List<ParkWithStatus>> = flowOf(ParkData.parks)
        .combine(visitDao.getAllVisits()) { parks, visits ->
            val visitsMap = visits.associateBy { it.parkId }

            parks.map { park ->
                val visit = visitsMap[park.id]
                ParkWithStatus(park, visit != null, visit?.visitedDate)
            }
        }

    suspend fun toggleVisit(parkId: String, isVisited: Boolean) {
        if (isVisited) {
            visitDao.delete(parkId)
        } else {
            visitDao.insert(Visit(parkId))
        }
    }

    suspend fun getParkById(parkId: String): Park? {
        return ParkData.parks.find { it.id == parkId }
    }
}
