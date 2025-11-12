package com.example.data.network

import com.example.domain.model.Course
import retrofit2.http.GET

interface CoursesApi {
    @GET("u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    suspend fun getCourses(): CoursesResponse
}

data class CoursesResponse(
    val courses: List<Course> = emptyList()
)
