package com.hellotractor.android.notes.farms

import com.hellotractor.notes.domain.models.Note


enum class OrderType { DATE, CATEGORY }

data class FarmsState(
    val isLoading: Boolean = false,
    val notes: List<Note> = emptyList(),
    val selectedOrder: OrderType = OrderType.DATE,
    val errorMessage: String? = null
)

sealed class FarmsEvent {
    object Load : FarmsEvent()
    object Refresh : FarmsEvent()
    data class ChangeOrder(val order: OrderType) : FarmsEvent()
}

sealed class FarmsAction {
    data class ShowError(val message: String) : FarmsAction()
}
