package com.example.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavoriteIds(): Flow<Set<Int>>
    suspend fun toggleFavorite(courseId: Int)
    suspend fun isFavorite(courseId: Int): Boolean
}
