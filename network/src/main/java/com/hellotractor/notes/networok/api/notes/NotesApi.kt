package com.hellotractor.notes.networok.api.notes

import retrofit2.http.GET
import com.hellotractor.notes.networok.api.notes.models.NoteResponse

interface NotesApi {

    @GET("notes")
    suspend fun fetchNotes(): List<NoteResponse>
}
