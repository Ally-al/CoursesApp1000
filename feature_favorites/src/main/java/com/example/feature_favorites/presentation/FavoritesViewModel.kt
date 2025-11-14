package com.example.feature_favorites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Course
import com.example.domain.usecase.FavoritesUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val favoritesUseCases: FavoritesUseCases
) : ViewModel() {

    val favoriteCourses: LiveData<List<Course>> =
        favoritesUseCases.getFavoriteCourses.execute().asLiveData()

    fun toggleFavorite(courseId: Int) {
        viewModelScope.launch {
            favoritesUseCases.toggleFavorite.execute(courseId)
        }
    }
}
