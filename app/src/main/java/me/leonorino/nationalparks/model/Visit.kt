package me.leonorino.nationalparks.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "visits")
data class Visit (
    @PrimaryKey val parkId: String
)