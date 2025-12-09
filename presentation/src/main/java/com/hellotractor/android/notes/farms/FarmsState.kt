package com.hellotractor.android.notes.farms

import com.hellotractor.notes.domain.models.Note

data class FarmsState(
    val isLoading: Boolean = false,
    val notes: List<Note> = emptyList(),
    val selectedOrder: OrderType = OrderType.DATE,
    val errorMessage: String? = null
)
