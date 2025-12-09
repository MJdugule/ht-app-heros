package com.hellotractor.android.notes.farms

import com.hellotractor.notes.domain.models.Note


enum class OrderType { DATE, CATEGORY }

sealed class FarmsEvent {
    object Load : FarmsEvent()
    object Refresh : FarmsEvent()
    data class ChangeOrder(val order: OrderType) : FarmsEvent()
}

sealed class FarmsAction {
    data class ShowError(val message: String) : FarmsAction()
}
