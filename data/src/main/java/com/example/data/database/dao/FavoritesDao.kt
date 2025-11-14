package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entity.FavoriteCourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorite_courses")
    fun getAllFavorites(): Flow<List<FavoriteCourseEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_courses WHERE courseId = :courseId)")
    suspend fun isFavorite(courseId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteCourseEntity)

    @Query("DELETE FROM favorite_courses WHERE courseId = :courseId")
    suspend fun removeFavorite(courseId: Int)
}
