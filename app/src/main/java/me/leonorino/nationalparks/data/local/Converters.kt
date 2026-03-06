package me.leonorino.nationalparks.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import me.leonorino.nationalparks.model.USState

class Converters {
    @TypeConverter
    fun fromStateList(value: List<USState>) = Json.encodeToString(value)

    @TypeConverter
    fun toStateList(value: String) = Json.decodeFromString<List<USState>>(value)

    @TypeConverter
    fun fromStringList(value: List<String>) = Json.encodeToString(value)

    @TypeConverter
    fun toStringList(value: String) = Json.decodeFromString<List<String>>(value)
}
