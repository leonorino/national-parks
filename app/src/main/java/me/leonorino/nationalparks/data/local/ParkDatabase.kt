package me.leonorino.nationalparks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.leonorino.nationalparks.model.Park

@Database(entities = [Park::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ParkDatabase : RoomDatabase() {
    abstract fun parkDao() : ParkDao
}
