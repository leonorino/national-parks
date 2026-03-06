package me.leonorino.nationalparks.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "parks")
data class Park(
    @PrimaryKey val id: String,
    val fullName: String,
    val states: List<USState>,
    val description: String,
    val cardImageUrl: String,
    val expandedImageUrl: String,
    val categories: List<String>,
)
