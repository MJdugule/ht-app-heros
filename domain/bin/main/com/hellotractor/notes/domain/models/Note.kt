package com.hellotractor.notes.domain.models

data class Note(
    val id: Long,
    val title: String,
    val dateCreated: String,
    val author: String,
    val content: String,
    val comments: List<String>,
    val tags: List<String>,
    val type: NoteType
) {
    enum class NoteType {
        IMPORTANT,
        NORMAL,
        PRIVATE
    }
}
