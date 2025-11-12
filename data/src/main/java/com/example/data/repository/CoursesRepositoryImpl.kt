package com.example.data.repository

import com.example.data.network.CoursesApi
import com.example.domain.model.Course
import com.example.domain.repository.CoursesRepository

class CoursesRepositoryImpl(
    private val api: CoursesApi
) : CoursesRepository {
    override suspend fun getCourses(): List<Course> {
        return api.getCourses().courses
    }
}
