package com.conamobile.konspektor.ui.history.di

import android.content.Context
import androidx.room.Room
import com.conamobile.konspektor.ui.history.database.HistoryDao
import com.conamobile.konspektor.ui.history.database.HistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideHistoryDatabase(@ApplicationContext context: Context): HistoryDatabase {
        return Room.databaseBuilder(context, HistoryDatabase::class.java, "history.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(appDatabase: HistoryDatabase): HistoryDao = appDatabase.historyDao()

}