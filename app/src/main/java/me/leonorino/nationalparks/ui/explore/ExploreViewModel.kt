package me.leonorino.nationalparks.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import me.leonorino.nationalparks.data.repository.ParkRepository
import me.leonorino.nationalparks.model.Park

sealed class ExploreUiState {
    object Loading : ExploreUiState()
    data class Success(
        val parks: List<Park>,
        val categories: List<Int>,
        val selectedCategory: Int? = null
    ) : ExploreUiState()
    data class Error(val messageResId: Int) : ExploreUiState()
}

class ExploreViewModel(private val repository: ParkRepository) : ViewModel() {
    private val _selectedCategory = MutableStateFlow<Int?>(null)

    val uiState: StateFlow<ExploreUiState> = combine(
        repository.allParks,
        _selectedCategory
    ) { parks, selectedCategory ->
        if (parks.isEmpty()) {
            ExploreUiState.Loading
        } else {
            val categories = parks.flatMap { it.categoryResIds }.distinct().sorted()
            val filteredParks = if (selectedCategory == null) {
                parks
            } else {
                parks.filter { it.categoryResIds.contains(selectedCategory) }
            }
            ExploreUiState.Success(
                parks = filteredParks,
                categories = categories,
                selectedCategory = selectedCategory
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ExploreUiState.Loading
    )

    fun onCategorySelected(category: Int?) {
        _selectedCategory.value = category
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
