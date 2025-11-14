package com.example.feature_home.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.utils.parseIsoDateOrNull
import com.example.domain.model.Course
import com.example.domain.usecase.CoursesUseCases
import com.example.domain.usecase.FavoritesUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val coursesUseCases: CoursesUseCases,
    private val favoritesUseCases: FavoritesUseCases
) : ViewModel() {

    private val _sortedCourses = MutableStateFlow<List<Course>?>(null)

    val courses: LiveData<List<Course>> = combine(
        coursesUseCases.observeCoursesWithFavorites.execute(),
        _sortedCourses
    ) { observedCourses, sortedCourses ->
        sortedCourses ?: observedCourses
    }.asLiveData()

    init {
        loadCourses()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            try {
                val loaded = coursesUseCases.getCourses.execute()
                coursesUseCases.syncFavoritesFromServer.execute(loaded)
            } catch (e: Throwable) {
                Log.e("HomeViewModel", "Error loading courses", e)
            }
        }
    }

    fun toggleFavorite(courseId: Int) {
        viewModelScope.launch {
            favoritesUseCases.toggleFavorite.execute(courseId)
        }
    }

    fun sortCoursesByDateDesc() {
        val current = courses.value ?: return
        val sorted = current.sortedByDescending { parseIsoDateOrNull(it.publishDate) ?: Date(0) }
        _sortedCourses.value = sorted
    }
}
