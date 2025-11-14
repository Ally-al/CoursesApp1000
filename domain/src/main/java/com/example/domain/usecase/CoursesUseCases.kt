package com.example.domain.usecase

import com.example.domain.model.Course
import com.example.domain.repository.CoursesRepository
import com.example.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

data class CoursesUseCases(
    val getCourses: GetCoursesUseCase,
    val observeCoursesWithFavorites: ObserveCoursesWithFavoritesUseCase,
    val syncFavoritesFromServer: SyncFavoritesFromServerUseCase
)

class GetCoursesUseCase(
    private val coursesRepository: CoursesRepository
) {
    suspend fun execute(): List<Course> {
        return coursesRepository.getCourses()
    }
}

class ObserveCoursesWithFavoritesUseCase(
    private val coursesRepository: CoursesRepository,
    private val favoritesRepository: FavoritesRepository
) {
    fun execute(): Flow<List<Course>> {
        return combine(
            coursesRepository.observeCourses(),
            favoritesRepository.getFavoriteIds()
        ) { courses, favoriteIds ->
            courses.map { course ->
                course.copy(hasLike = favoriteIds.contains(course.id))
            }
        }
    }
}

class SyncFavoritesFromServerUseCase(
    private val coursesRepository: CoursesRepository,
    private val favoritesRepository: FavoritesRepository
) {
    suspend fun execute(loadedCourses: List<Course>) {
        loadedCourses.forEach { course ->
            if (course.hasLike) {
                val isAlreadyFavorite = favoritesRepository.isFavorite(course.id)
                if (!isAlreadyFavorite) {
                    favoritesRepository.toggleFavorite(course.id)
                }
            }
        }
    }
}
