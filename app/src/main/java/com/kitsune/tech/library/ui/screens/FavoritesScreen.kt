package com.kitsune.tech.library.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kitsune.tech.library.data.database.Book
import com.kitsune.tech.library.data.database.FavoriteRepository
import com.kitsune.tech.library.ui.components.BottomNavigationBar
import com.kitsune.tech.library.ui.components.BookListItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    userId: Int,
    favoriteRepository: FavoriteRepository,
    onNavigateToHome: () -> Unit,
    onNavigateToLibrary: () -> Unit,
    onNavigateToBookDetails: (Int) -> Unit,
    onNavigateToProfile: () -> Unit
) {
    var favorites by remember { mutableStateOf<List<Book>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            favorites = favoriteRepository.getUserFavorites(userId)
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorites") }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = "favorites",
                onNavigateToHome = onNavigateToHome,
                onNavigateToLibrary = onNavigateToLibrary,
                onNavigateToFavorites = {},
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
        } else if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No favorites yet",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favorites) { book ->
                    BookListItem(
                        book = book,
                        onClick = { onNavigateToBookDetails(book.id) }
                    )
                }
            }
        }
    }
}
