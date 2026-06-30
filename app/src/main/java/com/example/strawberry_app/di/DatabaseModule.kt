package com.example.strawberry_app.di

import android.content.Context
import androidx.room.Room
import com.example.strawberry_app.data.db.AppDatabase
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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "strawberry.db"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    @Provides
    @Singleton
    fun providePlaylistDao(db: AppDatabase) = db.playlistDao()

    @Provides
    @Singleton
    fun provideSongDao(db: AppDatabase) = db.songDao()

    @Provides
    @Singleton
    fun providePlaylistSongDao(db: AppDatabase) = db.playlistSongDao()
}