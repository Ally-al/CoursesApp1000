package com.example.data.di

import com.example.data.repository.CoursesRepositoryImpl
import com.example.data.repository.FavoritesRepositoryImpl
import com.example.domain.repository.CoursesRepository
import com.example.domain.repository.FavoritesRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindCoursesRepository(impl: CoursesRepositoryImpl): CoursesRepository

    @Binds
    fun bindFavoritesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository
}
