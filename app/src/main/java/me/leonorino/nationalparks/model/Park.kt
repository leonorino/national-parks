package me.leonorino.nationalparks.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "parks")
data class Park(
    @PrimaryKey val id: String,
    val nameResId: Int,
    val descriptionResId: Int,
    val categoryResIds: List<Int>,
    val states: List<USState>,
    val cardImageUrl: String,
    val expandedImageUrl: String,
    val elevationMeters: Int,
    val areaSqKm: Double,
    val yearlyVisitors: Int
) {
    @get:Ignore
    val elevationFeet: Int
        get() = (elevationMeters * 3.28084).toInt()

    @get:Ignore
    val areaSqMiles: Double
        get() = areaSqKm * 0.386102
}
