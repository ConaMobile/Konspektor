package com.conamobile.konspektor.ui.history.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.conamobile.konspektor.ui.history.model.HistoryModel

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(history: HistoryModel)

    @Delete(entity = HistoryModel::class)
    suspend fun deleteData(history: HistoryModel)

    @Query("SELECT * FROM history")
    fun getNotes(): LiveData<List<HistoryModel>>

    @Query("DELETE FROM history")
    suspend fun deleteAll()
}