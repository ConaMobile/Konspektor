package com.conamobile.konspektor.ui.history.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.conamobile.konspektor.ui.history.model.HistoryModel
import com.conamobile.konspektor.ui.history.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private var historyRepository: HistoryRepository) :
    ViewModel() {

    fun addNote(note: HistoryModel) = viewModelScope.launch(Dispatchers.IO) {
        historyRepository.saveNote(note)
    }

    fun getAllNote() = historyRepository.getAllNotes()


    fun deleteNote(history: HistoryModel) = viewModelScope.launch(Dispatchers.IO) {
        historyRepository.deleteNote(history)
    }

    fun deleteAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        historyRepository.deleteAll()
    }
}