package com.hellotractor.notes.domain.repositories

import com.hellotractor.notes.domain.models.Note

interface NotesRepository {
    suspend fun fetchNotes(): List<Note>
}
