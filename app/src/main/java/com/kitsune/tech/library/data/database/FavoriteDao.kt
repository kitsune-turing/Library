package com.kitsune.tech.library.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE userId = :userId AND bookId = :bookId")
    suspend fun deleteFavorite(userId: Int, bookId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE userId = :userId AND bookId = :bookId)")
    suspend fun isFavorite(userId: Int, bookId: Int): Boolean

    @Query("""
        SELECT b.* FROM books b
        INNER JOIN favorites f ON b.id = f.bookId
        WHERE f.userId = :userId
        ORDER BY f.addedAt DESC
    """)
    suspend fun getUserFavorites(userId: Int): List<Book>
}
