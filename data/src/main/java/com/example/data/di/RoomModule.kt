package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.AppDatabase
import com.example.data.database.dao.FavoritesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RoomModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "courses_database"
        ).build()

    @Provides
    @Singleton
    @JvmStatic
    fun provideFavoritesDao(db: AppDatabase): FavoritesDao =
        db.favoritesDao()
}
