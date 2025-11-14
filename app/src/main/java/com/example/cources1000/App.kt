package com.example.cources1000

import android.app.Application
import com.example.cources1000.di.AppComponent
import com.example.cources1000.di.DaggerAppComponent
import com.example.feature_home.di.HomeDependencies
import com.example.feature_favorites.di.FavoritesDependencies

class App : Application(), HomeDependencies, FavoritesDependencies {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set
    }

    override fun coursesUseCases() = appComponent.coursesUseCases()
    override fun favoritesUseCases() = appComponent.favoritesUseCases()
}
