package com.example.data.di

import com.example.data.network.CoursesApi
import com.example.data.repository.CoursesRepositoryImpl
import com.example.domain.repository.CoursesRepository
import com.example.domain.usecase.GetCoursesUseCase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    private const val BASE_URL = "https://drive.usercontent.google.com/"

    @Provides
    @Singleton
    @JvmStatic
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @JvmStatic
    fun provideCoursesApi(retrofit: Retrofit): CoursesApi =
        retrofit.create(CoursesApi::class.java)

    @Provides
    @Singleton
    @JvmStatic
    fun provideCoursesRepository(api: CoursesApi): CoursesRepository =
        CoursesRepositoryImpl(api)

    @Provides
    @Singleton
    @JvmStatic
    fun provideGetCoursesUseCase(repository: CoursesRepository): GetCoursesUseCase =
        GetCoursesUseCase(repository)
}
