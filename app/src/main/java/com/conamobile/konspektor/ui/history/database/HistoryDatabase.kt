package com.conamobile.konspektor.ui.history.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.conamobile.konspektor.ui.history.model.HistoryModel

@Database(entities = [HistoryModel::class], version = 1, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

}