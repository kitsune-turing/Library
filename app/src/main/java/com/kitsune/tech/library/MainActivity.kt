package com.kitsune.tech.library

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.kitsune.tech.library.data.database.*
import com.kitsune.tech.library.navigation.NavGraph
import com.kitsune.tech.library.ui.theme.LibraryTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var userRepository: UserRepository
    private lateinit var bookRepository: BookRepository
    private lateinit var readingHistoryRepository: ReadingHistoryRepository
    private lateinit var favoriteRepository: FavoriteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        userRepository = UserRepository(database.userDao())
        bookRepository = BookRepository(database.bookDao())
        readingHistoryRepository = ReadingHistoryRepository(database.readingHistoryDao())
        favoriteRepository = FavoriteRepository(database.favoriteDao())

        lifecycleScope.launch {
            val bookCount = database.bookDao().getAllBooks().size
            if (bookCount == 0) {
                database.bookDao().insertBooks(SampleData.getSampleBooks())
            }
        }

        setContent {
            LibraryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    NavGraph(
                        navController = navController,
                        userRepository = userRepository,
                        bookRepository = bookRepository,
                        favoriteRepository = favoriteRepository
                    )
                }
            }
        }
    }
}
