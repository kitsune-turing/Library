package com.kitsune.tech.library.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<Book>)

    @Query("SELECT * FROM books WHERE id = :bookId LIMIT 1")
    suspend fun getBookById(bookId: Int): Book?

    @Query("SELECT * FROM books ORDER BY addedAt DESC")
    suspend fun getAllBooks(): List<Book>

    @Query("SELECT * FROM books WHERE title LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%' OR genre LIKE '%' || :query || '%' ORDER BY title ASC")
    suspend fun searchBooks(query: String): List<Book>
}
