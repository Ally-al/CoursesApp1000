package com.example.feature_favorites.di

import com.example.domain.usecase.FavoritesUseCases

interface FavoritesDependencies {
    fun favoritesUseCases(): FavoritesUseCases
}
