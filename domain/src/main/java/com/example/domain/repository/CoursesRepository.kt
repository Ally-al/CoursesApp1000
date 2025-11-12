package com.example.domain.repository

import com.example.domain.model.Course

interface CoursesRepository {
    suspend fun getCourses(): List<Course>
}
