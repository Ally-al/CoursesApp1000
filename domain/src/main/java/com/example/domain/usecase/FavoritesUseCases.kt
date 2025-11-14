package com.example.domain.usecase

import com.example.domain.model.Course
import com.example.domain.repository.CoursesRepository
import com.example.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

data class FavoritesUseCases(
    val getFavoriteCourses: GetFavoriteCoursesUseCase,
    val toggleFavorite: ToggleFavoriteUseCase
)

class GetFavoriteCoursesUseCase(
    private val coursesRepository: CoursesRepository,
    private val favoritesRepository: FavoritesRepository
) {
    fun execute(): Flow<List<Course>> {
        return combine(
            coursesRepository.observeCourses(),
            favoritesRepository.getFavoriteIds()
        ) { courses, favoriteIds ->
            courses
                .filter { favoriteIds.contains(it.id) }
                .map { it.copy(hasLike = true) }
        }
    }
}

class ToggleFavoriteUseCase(
    private val favoritesRepository: FavoritesRepository
) {
    suspend fun execute(courseId: Int) {
        favoritesRepository.toggleFavorite(courseId)
    }
}
