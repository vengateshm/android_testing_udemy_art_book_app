package dev.vengateshm.artbookappandroidtestingudemy.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.vengateshm.artbookappandroidtestingudemy.roomdb.ArtDatabase
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    @Singleton
    @Named("test_db")
    fun providesInMemoryRoomDB(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context,
            ArtDatabase::class.java
        ).allowMainThreadQueries().build()
}