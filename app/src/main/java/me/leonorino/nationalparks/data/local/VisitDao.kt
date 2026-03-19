package me.leonorino.nationalparks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.leonorino.nationalparks.model.Visit

@Dao
interface VisitDao {
    @Query("SELECT * FROM visits")
    fun getAllVisits(): Flow<List<Visit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(visit: Visit)

    @Query("DELETE FROM visits WHERE parkId = :parkId")
    suspend fun delete(parkId: String)
}