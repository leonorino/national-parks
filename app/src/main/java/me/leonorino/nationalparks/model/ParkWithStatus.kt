package me.leonorino.nationalparks.model

data class ParkWithStatus (
    val park: Park,
    val visit: Visit? = null
) {
    val isVisited: Boolean
        get() = visit != null

    val visitedDate: Long?
        get() = visit?.visitedDate
}
