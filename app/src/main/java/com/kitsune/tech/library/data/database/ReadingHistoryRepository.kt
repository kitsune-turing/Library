package com.kitsune.tech.library.data.database

class ReadingHistoryRepository(private val readingHistoryDao: ReadingHistoryDao) {
    suspend fun insertReadingHistory(readingHistory: ReadingHistory) =
        readingHistoryDao.insertReadingHistory(readingHistory)

    suspend fun getRecentlyReadBooks(userId: Int): List<Book> =
        readingHistoryDao.getRecentlyReadBooks(userId)

    suspend fun getLastReadBook(userId: Int): ReadingHistory? =
        readingHistoryDao.getLastReadBook(userId)
}
