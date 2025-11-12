package com.example.feature_favorites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.core.FavoritesManager
import com.example.domain.model.Course

class FavoritesViewModel : ViewModel() {

    val favoriteCourses: LiveData<List<Course>> = FavoritesManager.favoriteCourses

    fun toggleFavorite(courseId: Int) {
        FavoritesManager.toggleFavorite(courseId)
    }
}
