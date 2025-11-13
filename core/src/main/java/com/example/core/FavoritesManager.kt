package com.example.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.model.Course

object FavoritesManager {

    private val allCourses = mutableListOf<Course>()

    private val _allCoursesLive = MutableLiveData<List<Course>>(emptyList())
    val allCoursesLive: LiveData<List<Course>> = _allCoursesLive

    private val _favoriteCourses = MutableLiveData<List<Course>>(emptyList())
    val favoriteCourses: LiveData<List<Course>> = _favoriteCourses

    fun setCourses(courses: List<Course>) {
        allCourses.clear()
        allCourses.addAll(courses)
        _allCoursesLive.value = allCourses.toList()
        updateFavorites()
    }

    fun toggleFavorite(courseId: Int) {
        val course = allCourses.find { it.id == courseId } ?: return
        course.hasLike = !course.hasLike
        updateFavorites()
        _allCoursesLive.value = allCourses.toList()
    }

    private fun updateFavorites() {
        _favoriteCourses.value = allCourses.filter { it.hasLike }
    }
}
