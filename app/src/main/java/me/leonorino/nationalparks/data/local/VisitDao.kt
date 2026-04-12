package me.leonorino.nationalparks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.leonorino.nationalparks.model.Visit

@Dao
interface VisitDao {
    @Query("SELECT * FROM visits")
    fun getAllVisits(): Flow<List<Visit>>

    @Query("SELECT * FROM visits WHERE parkId = :parkId LIMIT 1")
    suspend fun getVisitByParkId(parkId: String): Visit?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(visit: Visit)

    @Update
    suspend fun update(visit: Visit)

    @Query("DELETE FROM visits WHERE parkId = :parkId")
    suspend fun delete(parkId: String)
}
