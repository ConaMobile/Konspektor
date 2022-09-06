package com.conamobile.konspektor.ui.history.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var date: String? = null,
    var text: String? = null,
)