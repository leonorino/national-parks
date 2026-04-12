package me.leonorino.nationalparks.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.leonorino.nationalparks.NationalParksApplication
import me.leonorino.nationalparks.data.repository.ParkRepository
import me.leonorino.nationalparks.model.Park
import me.leonorino.nationalparks.model.Visit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class DetailsUiState(
    val visit: Visit? = null,
    val isEditorVisible: Boolean = false,
    val isDeleteDialogVisible: Boolean = false,
    val visitedDate: Long = System.currentTimeMillis(),
    val notesInput: String = "",
    val ratingInput: String = "",
    val ratingError: Boolean = false
) {
    val isVisited: Boolean
        get() = visit != null
}

object VisitDateFormatter {
    fun formatForDisplay(timestamp: Long): String {
        return SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(Date(timestamp))
    }
}

class DetailsViewModel(private val repository: ParkRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun loadVisit(parkId: String) {
        viewModelScope.launch {
            val visit = repository.getVisitByParkId(parkId)
            _uiState.value = visit.toUiState()
        }
    }

    suspend fun getPark(parkId: String): Park? {
        return repository.getParkById(parkId)
    }

    fun showCreateEditor() {
        _uiState.value = _uiState.value.copy(
            isEditorVisible = true,
            isDeleteDialogVisible = false,
            visitedDate = System.currentTimeMillis(),
            notesInput = "",
            ratingInput = "",
            ratingError = false
        )
    }

    fun showEditEditor() {
        val visit = _uiState.value.visit ?: return
        _uiState.value = _uiState.value.copy(
            isEditorVisible = true,
            isDeleteDialogVisible = false,
            visitedDate = visit.visitedDate,
            notesInput = visit.notes,
            ratingInput = visit.rating?.toString().orEmpty(),
            ratingError = false
        )
    }

    fun dismissEditor() {
        _uiState.value = _uiState.value.copy(
            isEditorVisible = false,
            ratingError = false
        )
    }

    fun updateVisitedDate(value: Long) {
        _uiState.value = _uiState.value.copy(visitedDate = value)
    }

    fun updateNotesInput(value: String) {
        _uiState.value = _uiState.value.copy(notesInput = value)
    }

    fun updateRatingInput(value: String) {
        _uiState.value = _uiState.value.copy(
            ratingInput = value.filter { it.isDigit() }.take(1),
            ratingError = false
        )
    }

    fun saveVisit(parkId: String) {
        val currentState = _uiState.value
        val rating = currentState.ratingInput.toIntOrNull()
        val isRatingInvalid = rating != null && rating !in 1..5

        if (isRatingInvalid) {
            _uiState.value = currentState.copy(
                ratingError = isRatingInvalid
            )
            return
        }

        viewModelScope.launch {
            val existingVisit = _uiState.value.visit
            if (existingVisit == null) {
                repository.addVisit(
                    parkId = parkId,
                    visitedDate = currentState.visitedDate,
                    notes = _uiState.value.notesInput,
                    rating = rating
                )
            } else {
                repository.updateVisit(
                    existingVisit.copy(
                        visitedDate = currentState.visitedDate,
                        notes = _uiState.value.notesInput,
                        rating = rating
                    )
                )
            }
            loadVisit(parkId)
            dismissEditor()
        }
    }

    fun showDeleteDialog() {
        _uiState.value = _uiState.value.copy(isDeleteDialogVisible = true)
    }

    fun dismissDeleteDialog() {
        _uiState.value = _uiState.value.copy(isDeleteDialogVisible = false)
    }

    fun deleteVisit(parkId: String) {
        viewModelScope.launch {
            repository.deleteVisit(parkId)
            _uiState.value = DetailsUiState()
        }
    }

    private fun Visit?.toUiState(): DetailsUiState {
        return if (this == null) {
            DetailsUiState()
        } else {
            DetailsUiState(
                visit = this,
                visitedDate = visitedDate,
                notesInput = notes,
                ratingInput = rating?.toString().orEmpty()
            )
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
