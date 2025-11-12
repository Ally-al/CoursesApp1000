package com.example.feature_home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.GetCoursesUseCase
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(getCoursesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
