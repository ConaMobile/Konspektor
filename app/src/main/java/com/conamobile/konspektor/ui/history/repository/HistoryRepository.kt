package com.conamobile.konspektor.ui.history.repository

import com.conamobile.konspektor.ui.history.database.HistoryDatabase
import com.conamobile.konspektor.ui.history.model.HistoryModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryRepository @Inject constructor(private val db: HistoryDatabase) {

    fun getAllNotes() = db.historyDao().getNotes()

    suspend fun saveNote(note: HistoryModel) = db.historyDao().saveNote(note)

    suspend fun deleteAll() = db.historyDao().deleteAll()

    suspend fun deleteNote(history: HistoryModel) = db.historyDao().deleteData(history)
}