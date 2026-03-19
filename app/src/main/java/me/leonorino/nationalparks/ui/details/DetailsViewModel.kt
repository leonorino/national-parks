package me.leonorino.nationalparks.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import me.leonorino.nationalparks.NationalParksApplication
import me.leonorino.nationalparks.data.repository.ParkRepository
import me.leonorino.nationalparks.model.Park

class DetailsViewModel(private val repository: ParkRepository) : ViewModel() {
    
    private val _isVisited = MutableStateFlow(false)
    val isVisited: StateFlow<Boolean> = _isVisited.asStateFlow()

    fun loadParkStatus(parkId: String) {
        viewModelScope.launch {
            repository.allParksWithStatus.collect { parks ->
                val park = parks.find { it.park.id == parkId }
                _isVisited.value = park?.isVisited ?: false
            }
        }
    }

    suspend fun getPark(parkId: String): Park? {
        return repository.getParkById(parkId)
    }

    fun toggleVisit(parkId: String) {
        val currentStatus = _isVisited.value
        viewModelScope.launch {
            repository.toggleVisit(parkId, currentStatus)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NationalParksApplication)
                DetailsViewModel(repository = application.container.repository)
            }
        }
    }
}
