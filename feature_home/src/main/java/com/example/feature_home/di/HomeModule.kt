package com.example.feature_home.di

import com.example.feature_home.presentation.HomeViewModelFactory
import com.example.domain.usecase.CoursesUseCases
import com.example.domain.usecase.FavoritesUseCases
import dagger.Module
import dagger.Provides

@Module
object HomeModule {

    @Provides
    fun provideHomeViewModelFactory(
        coursesUseCases: CoursesUseCases,
        favoritesUseCases: FavoritesUseCases
    ): HomeViewModelFactory = HomeViewModelFactory(coursesUseCases, favoritesUseCases)
}
