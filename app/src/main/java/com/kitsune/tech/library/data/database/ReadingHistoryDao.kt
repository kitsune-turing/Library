package com.kitsune.tech.library.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReadingHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReadingHistory(readingHistory: ReadingHistory)

    @Query("""
        SELECT b.* FROM books b
        INNER JOIN reading_history rh ON b.id = rh.bookId
        WHERE rh.userId = :userId
        ORDER BY rh.lastReadAt DESC
        LIMIT 10
    """)
    suspend fun getRecentlyReadBooks(userId: Int): List<Book>

    @Query("SELECT * FROM reading_history WHERE userId = :userId ORDER BY lastReadAt DESC LIMIT 1")
    suspend fun getLastReadBook(userId: Int): ReadingHistory?
}
