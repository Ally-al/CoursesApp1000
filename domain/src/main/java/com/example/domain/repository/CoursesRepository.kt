package com.example.domain.repository

import com.example.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    suspend fun getCourses(): List<Course>
    fun observeCourses(): Flow<List<Course>>
}
