package com.example.feature_favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.FavoritesUseCases
import javax.inject.Inject

class FavoritesViewModelFactory @Inject constructor(
    private val favoritesUseCases: FavoritesUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(favoritesUseCases) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
