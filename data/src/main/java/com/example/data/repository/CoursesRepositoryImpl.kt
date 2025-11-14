package com.example.data.repository

import com.example.data.network.CoursesApi
import com.example.domain.model.Course
import com.example.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoursesRepositoryImpl @Inject constructor(
    private val api: CoursesApi
) : CoursesRepository {

    private val _coursesFlow = MutableStateFlow<List<Course>>(emptyList())

    override suspend fun getCourses(): List<Course> {
        val courses = api.getCourses().courses
        _coursesFlow.value = courses
        return courses
    }

    override fun observeCourses(): Flow<List<Course>> {
        return _coursesFlow.asStateFlow()
    }
}
