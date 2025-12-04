package com.hellotractor.android.notes.farms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellotractor.notes.domain.models.Note
import com.hellotractor.notes.domain.usecases.GetNotesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FarmsListViewModel @Inject constructor(
    private val getNotesListUseCase: GetNotesListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FarmsState())
    val state: StateFlow<FarmsState> = _state.asStateFlow()

    private val _actions = MutableSharedFlow<FarmsAction>()
    val actions: SharedFlow<FarmsAction> = _actions.asSharedFlow()

    init {
        sendEvent(FarmsEvent.Load)
    }

    fun sendEvent(event: FarmsEvent) {
        when (event) {
            is FarmsEvent.Load -> loadNotes(order = OrderType.DATE, useDelay = true)
            is FarmsEvent.Refresh -> loadNotes(order = _state.value.selectedOrder, useDelay = false)
            is FarmsEvent.ChangeOrder -> loadNotes(order = event.order, useDelay = false)
        }
    }

    private fun loadNotes(order: OrderType, useDelay: Boolean) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            try {
                if (useDelay) kotlinx.coroutines.delay(600)

                val raw = getNotesListUseCase.run(sortByDate = false)

                val notes = when (order) {
                    OrderType.DATE -> raw.sortedByDescending { it.dateCreated }
                    OrderType.CATEGORY -> raw.sortedBy { it.type.name }
                }

                _state.value = _state.value.copy(
                    isLoading = false,
                    notes = notes,
                    selectedOrder = order,
                    errorMessage = null
                )
            } catch (t: Throwable) {
                _state.value = _state.value.copy(notes = emptyList(), errorMessage = t.message ?: "Unknown error")
                _actions.emit(FarmsAction.ShowError(t.message ?: "Failed to load"))
            } finally {

                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}
