package com.example.feature_home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.utils.parseIsoDateOrNull
import com.example.domain.usecase.CoursesUseCases
import com.example.domain.usecase.FavoritesUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val coursesUseCases: CoursesUseCases,
    private val favoritesUseCases: FavoritesUseCases
) : ViewModel() {

    private val _sortDesc = MutableStateFlow(false)
    private val coursesSource = coursesUseCases.observeCoursesWithFavorites.execute()

    private val coursesStateFlow = combine(coursesSource, _sortDesc) { courses, sortDesc ->
        if (sortDesc) {
            courses.sortedByDescending { parseIsoDateOrNull(it.publishDate) ?: Date(0) }
        } else {
            courses
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val courses = coursesStateFlow.asLiveData()

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

    fun toggleSort() {
        _sortDesc.value = !_sortDesc.value
    }

    fun setSortDesc(enabled: Boolean) {
        _sortDesc.value = enabled
    }
}
