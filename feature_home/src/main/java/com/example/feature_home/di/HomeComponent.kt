package com.example.feature_home.di

import com.example.feature_home.presentation.HomeFragment
import dagger.Component

@HomeScope
@Component(
    modules = [HomeModule::class],
    dependencies = [HomeDependencies::class]
)
interface HomeComponent {
    fun inject(fragment: HomeFragment)

    @Component.Factory
    interface Factory {
        fun create(deps: HomeDependencies): HomeComponent
    }
}
