package com.kitsune.tech.library.data.database

class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(username: String, password: String): Boolean {
        return try {
            val existingUser = userDao.getUserByUsername(username)
            if (existingUser != null) {
                false
            } else {
                userDao.insertUser(User(username = username, password = password))
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    suspend fun loginUser(username: String, password: String): User? {
        return userDao.login(username, password)
    }

    suspend fun userExists(username: String): Boolean {
        return userDao.getUserByUsername(username) != null
    }

    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }
}
