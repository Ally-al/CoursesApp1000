package com.example.feature_home.di

import com.example.domain.usecase.CoursesUseCases
import com.example.domain.usecase.FavoritesUseCases

interface HomeDependencies {
    fun coursesUseCases(): CoursesUseCases
    fun favoritesUseCases(): FavoritesUseCases
}
