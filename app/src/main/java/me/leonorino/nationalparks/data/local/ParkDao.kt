package me.leonorino.nationalparks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.leonorino.nationalparks.model.Park

@Dao
interface ParkDao {
    @Query("SELECT * FROM parks")
    fun getAllParks(): Flow<List<Park>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParks(parks: List<Park>)

    @Query("SELECT COUNT(*) FROM parks")
    suspend fun getParkCount(): Int

    @Query("SELECT * FROM parks WHERE id = :parkId")
    suspend fun getParkById(parkId: String): Park?
}
