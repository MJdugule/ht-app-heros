package com.hellotractor.notes.domain.usecases

import com.hellotractor.notes.domain.models.Note
import com.hellotractor.notes.domain.repositories.NotesRepository
import javax.inject.Inject

class GetNotesListUseCase @Inject constructor(private val repository: NotesRepository) {

    suspend fun run(sortByDate: Boolean = false, sortByType: Note.NoteType? = null): List<Note> {
        var notes = repository.fetchNotes()

        sortByType?.let { type ->
            notes = notes.filter { it.type == type }
        }

        if (sortByDate) {
            notes = notes.sortedByDescending { it.dateCreated }
        }

        return notes
    }
}
