package me.leonorino.nationalparks.model

data class Park(
    val id: String,
    val nameResId: Int,
    val descriptionResId: Int,
    val categoryResIds: List<Int>,
    val states: List<USState>,
    val cardImageUrl: String,
    val badgeImageUrl: String,
    val expandedImageUrl: String,
    val latitude: Double,
    val longitude: Double,
    val elevationMeters: Int,
    val areaSqKm: Double,
    val yearlyVisitors: Int
) {
    val elevationFeet: Int
        get() = (elevationMeters * 3.28084).toInt()

    val areaSqMiles: Double
        get() = areaSqKm * 0.386102
}
