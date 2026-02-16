package com.kitsune.tech.library.data.database

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    suspend fun insertFavorite(favorite: Favorite) = favoriteDao.insertFavorite(favorite)

    suspend fun deleteFavorite(userId: Int, bookId: Int) =
        favoriteDao.deleteFavorite(userId, bookId)

    suspend fun isFavorite(userId: Int, bookId: Int): Boolean =
        favoriteDao.isFavorite(userId, bookId)

    suspend fun getUserFavorites(userId: Int): List<Book> =
        favoriteDao.getUserFavorites(userId)

    suspend fun toggleFavorite(userId: Int, bookId: Int) {
        if (isFavorite(userId, bookId)) {
            deleteFavorite(userId, bookId)
        } else {
            insertFavorite(Favorite(userId = userId, bookId = bookId))
        }
    }
}
