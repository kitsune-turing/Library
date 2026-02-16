package com.kitsune.tech.library.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kitsune.tech.library.data.database.Book
import com.kitsune.tech.library.data.database.BookRepository
import com.kitsune.tech.library.ui.components.BottomNavigationBar
import com.kitsune.tech.library.ui.components.BookGridItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    userId: Int,
    bookRepository: BookRepository,
    onNavigateToHome: () -> Unit,
    onNavigateToBookDetails: (Int) -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    var books by remember { mutableStateOf<List<Book>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            books = bookRepository.getAllBooks()
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Library") }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = "library",
                onNavigateToHome = onNavigateToHome,
                onNavigateToLibrary = {},
                onNavigateToFavorites = onNavigateToFavorites,
                onNavigateToProfile = onNavigateToProfile
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(books) { book ->
                    BookGridItem(
                        book = book,
                        onClick = { onNavigateToBookDetails(book.id) }
                    )
                }
            }
        }
    }
}
