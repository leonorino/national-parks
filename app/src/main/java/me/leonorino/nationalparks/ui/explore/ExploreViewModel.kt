package me.leonorino.nationalparks.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.leonorino.nationalparks.R
import me.leonorino.nationalparks.data.repository.ParkRepository
import me.leonorino.nationalparks.model.Park

sealed class ExploreUiState {
    object Loading : ExploreUiState()
    data class Success(val parks: List<Park>) : ExploreUiState()
    data class Error(val messageResId: Int) : ExploreUiState()
}

class ExploreViewModel(private val repository: ParkRepository) : ViewModel() {
    val uiState: StateFlow<ExploreUiState> = repository.allParks
        .map { parks ->
            if (parks.isEmpty()) ExploreUiState.Loading
            else ExploreUiState.Success(parks)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ExploreUiState.Loading
        )

    init {
        fetchParks()
    }

    private fun fetchParks() {
        viewModelScope.launch {
            repository.initializeDatabaseIfNeeded()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as me.leonorino.nationalparks.NationalParksApplication)
                ExploreViewModel(repository = application.container.repository)
            }
        }
    }
}
