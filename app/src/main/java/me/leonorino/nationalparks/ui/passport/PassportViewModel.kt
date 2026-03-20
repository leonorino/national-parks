package me.leonorino.nationalparks.ui.passport

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import me.leonorino.nationalparks.NationalParksApplication
import me.leonorino.nationalparks.data.repository.ParkRepository
import me.leonorino.nationalparks.model.ParkWithStatus

enum class SortOrder { RECENT, STATE, ALPHABETICAL }

data class PassportUiState(
    val parksWithStatus: List<ParkWithStatus> = emptyList(),
    val currentSort: SortOrder = SortOrder.RECENT,
    val totalParks: Int = 0,
    val visitedParks: Int = 0,
    val progressPercentage: Int = 0
)

class PassportViewModel(
    application: Application,
    private val repository: ParkRepository
) : AndroidViewModel(application) {

    private val _currentSort = MutableStateFlow(SortOrder.RECENT)

    val uiState: StateFlow<PassportUiState> = combine(
        repository.allParksWithStatus,
        _currentSort
    ) { parks, sortOrder ->
        val sortedParks = when (sortOrder) {
            SortOrder.RECENT -> parks.sortedWith(
                compareByDescending<ParkWithStatus> { it.visitedDate ?: 0L }
                    .thenBy { getApplication<Application>().getString(it.park.nameResId) }
            )
            SortOrder.STATE -> parks.sortedBy { it.park.states.firstOrNull()?.fullNameResId }
            SortOrder.ALPHABETICAL -> parks.sortedBy { getApplication<Application>().getString(it.park.nameResId) }
        }

        val visitedCount = parks.count { it.isVisited }
        val totalCount = parks.size
        val percentage = if (totalCount > 0) (visitedCount * 100) / totalCount else 0

        PassportUiState(
            parksWithStatus = sortedParks,
            currentSort = sortOrder,
            totalParks = totalCount,
            visitedParks = visitedCount,
            progressPercentage = percentage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PassportUiState()
    )

    fun onSortChanged(sortOrder: SortOrder) {
        _currentSort.value = sortOrder
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NationalParksApplication)
                PassportViewModel(
                    application = application,
                    repository = application.container.repository
                )
            }
        }
    }
}
