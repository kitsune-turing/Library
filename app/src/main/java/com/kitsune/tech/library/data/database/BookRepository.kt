package com.kitsune.tech.library.data.database

class BookRepository(private val bookDao: BookDao) {
    suspend fun insertBooks(books: List<Book>) = bookDao.insertBooks(books)

    suspend fun getBookById(bookId: Int): Book? = bookDao.getBookById(bookId)

    suspend fun getAllBooks(): List<Book> = bookDao.getAllBooks()

    suspend fun searchBooks(query: String): List<Book> = bookDao.searchBooks(query)
}
