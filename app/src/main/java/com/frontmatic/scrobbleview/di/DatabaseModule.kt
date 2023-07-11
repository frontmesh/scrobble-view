package com.frontmatic.scrobbleview.di

import android.content.Context
import androidx.room.Room
import com.frontmatic.scrobbleview.data.ScrobbleDatabase
import com.frontmatic.scrobbleview.data.repository.LocalDataSource
import com.frontmatic.scrobbleview.data.repository.impl.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ScrobbleDatabase::class.java,
        "scrobble_database"
    ).build()

    @Provides
    @Singleton
    fun providesLocalDataSource(
        database: ScrobbleDatabase
    ) : LocalDataSource {
        return LocalDataSourceImpl(database)
    }
}