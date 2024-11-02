package com.jonathan.slighfetchtakehome.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseBuilders {

    @Provides
    fun provideChannelDao(appDatabase: FetchObjectDatabase): FetchObjectDao {
        return appDatabase.fetchObjectDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): FetchObjectDatabase {
        return Room.databaseBuilder(
            appContext,
            FetchObjectDatabase::class.java,
            "RssReader"
        ).build()
    }
}