package me.leonorino.nationalparks.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import me.leonorino.nationalparks.NationalParksApplication
import me.leonorino.nationalparks.data.repository.ParkRepository
import me.leonorino.nationalparks.model.Park

class DetailsViewModel(private val repository: ParkRepository) : ViewModel() {
    suspend fun getPark(parkId: String): Park? {
        return repository.getParkById(parkId)
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
