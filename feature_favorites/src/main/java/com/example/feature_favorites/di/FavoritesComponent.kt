package com.example.feature_favorites.di

import com.example.feature_favorites.presentation.FavoritesFragment
import dagger.Component

@FavoritesScope
@Component(
    modules = [FavoritesModule::class],
    dependencies = [FavoritesDependencies::class]
)
interface FavoritesComponent {

    fun inject(fragment: FavoritesFragment)

    @Component.Factory
    interface Factory {
        fun create(
            deps: FavoritesDependencies,
            module: FavoritesModule
        ): FavoritesComponent
    }
}
