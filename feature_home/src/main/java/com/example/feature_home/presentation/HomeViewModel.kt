package com.example.feature_home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.FavoritesManager
import com.example.domain.model.Course
import com.example.domain.usecase.GetCoursesUseCase
import com.example.core.utils.parseIsoDateOrNull
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase
) : ViewModel() {

    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>> get() = _courses

    init {
        loadCourses()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            try {
                val allCourses = getCoursesUseCase()
                FavoritesManager.setCourses(allCourses)
                _courses.value = allCourses
            } catch (e: Throwable) {
                FavoritesManager.setCourses(emptyList())
                _courses.value = emptyList()
            }
        }
    }

    fun toggleFavorite(courseId: Int) {
        FavoritesManager.toggleFavorite(courseId)
        _courses.value = FavoritesManager.allCoursesLive.value
    }

    fun sortCoursesByDate() {
        val sorted = _courses.value?.sortedByDescending { parseIsoDateOrNull(it.publishDate) ?: Date(0) }
        _courses.value = sorted
    }
}
