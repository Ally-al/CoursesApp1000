package com.example.cources1000.di

import android.content.Context
import com.example.domain.usecase.CoursesUseCases
import com.example.domain.usecase.FavoritesUseCases
import com.example.data.di.NetworkModule
import com.example.data.di.RepositoryModule
import com.example.data.di.RoomModule
import com.example.data.di.UseCaseModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RoomModule::class,
        RepositoryModule::class,
        UseCaseModule::class
    ]
)
interface AppComponent {

    fun coursesUseCases(): CoursesUseCases
    fun favoritesUseCases(): FavoritesUseCases

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
