package com.example.data.di

import com.example.domain.repository.CoursesRepository
import com.example.domain.repository.FavoritesRepository
import com.example.domain.usecase.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object UseCaseModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideCoursesUseCases(
        coursesRepository: CoursesRepository,
        favoritesRepository: FavoritesRepository
    ): CoursesUseCases =
        CoursesUseCases(
            getCourses = GetCoursesUseCase(coursesRepository),
            observeCoursesWithFavorites = ObserveCoursesWithFavoritesUseCase(
                coursesRepository,
                favoritesRepository
            ),
            syncFavoritesFromServer = SyncFavoritesFromServerUseCase(
                coursesRepository,
                favoritesRepository = favoritesRepository
            )
        )

    @Provides
    @Singleton
    @JvmStatic
    fun provideFavoritesUseCases(
        coursesRepository: CoursesRepository,
        favoritesRepository: FavoritesRepository
    ): FavoritesUseCases =
        FavoritesUseCases(
            getFavoriteCourses = GetFavoriteCoursesUseCase(
                coursesRepository,
                favoritesRepository
            ),
            toggleFavorite = ToggleFavoriteUseCase(favoritesRepository)
        )
}
