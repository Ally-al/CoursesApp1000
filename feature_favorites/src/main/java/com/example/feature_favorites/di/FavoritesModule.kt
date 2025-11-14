package com.example.feature_favorites.di

import com.example.feature_favorites.presentation.FavoritesViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class FavoritesModule(private val deps: FavoritesDependencies) {

    @Provides
    fun provideFavoritesUseCases() = deps.favoritesUseCases()

    @Provides
    fun provideFavoritesViewModelFactory(): FavoritesViewModelFactory =
        FavoritesViewModelFactory(deps.favoritesUseCases())
}
