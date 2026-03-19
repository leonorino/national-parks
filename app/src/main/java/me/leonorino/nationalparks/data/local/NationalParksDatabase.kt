package me.leonorino.nationalparks.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.leonorino.nationalparks.model.Visit

@Database(
    version = 1,
    entities = [Visit::class],
    exportSchema = false
)
abstract class NationalParksDatabase : RoomDatabase() {
    abstract fun visitDao() : VisitDao

    companion object {
        @Volatile
        private var Instance: NationalParksDatabase? = null

        fun getDatabase(context: Context): NationalParksDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    NationalParksDatabase::class.java,
                    "national_parks_database"
                ).build().also { Instance = it }
            }
        }
    }
}