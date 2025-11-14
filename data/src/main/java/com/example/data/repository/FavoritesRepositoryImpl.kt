package com.example.data.repository

import com.example.data.database.dao.FavoritesDao
import com.example.data.database.entity.FavoriteCourseEntity
import com.example.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesDao: FavoritesDao
) : FavoritesRepository {

    override fun getFavoriteIds(): Flow<Set<Int>> {
        return favoritesDao.getAllFavorites()
            .map { favorites ->
                favorites.map { it.courseId }.toSet()
            }
    }

    override suspend fun toggleFavorite(courseId: Int) {
        val isFav = favoritesDao.isFavorite(courseId)
        if (isFav) {
            favoritesDao.removeFavorite(courseId)
        } else {
            favoritesDao.addFavorite(FavoriteCourseEntity(courseId))
        }
    }

    override suspend fun isFavorite(courseId: Int): Boolean {
        return favoritesDao.isFavorite(courseId)
    }
}
