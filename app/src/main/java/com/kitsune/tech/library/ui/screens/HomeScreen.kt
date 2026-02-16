package com.kitsune.tech.library.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.kitsune.tech.library.R
import com.kitsune.tech.library.data.database.Book
import com.kitsune.tech.library.data.database.BookRepository
import com.kitsune.tech.library.data.database.UserRepository
import com.kitsune.tech.library.ui.components.BottomNavigationBar
import com.kitsune.tech.library.ui.components.BookCard
import com.kitsune.tech.library.ui.components.ErrorState
import com.kitsune.tech.library.ui.components.FeaturedBookCard
import com.kitsune.tech.library.ui.theme.GoldLibrary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userId: Int,
    userRepository: UserRepository,
    bookRepository: BookRepository,
    onNavigateToSearch: () -> Unit,
    onNavigateToBookDetails: (Int) -> Unit,
    onNavigateToLibrary: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var recommendedBooks by remember { mutableStateOf<List<Book>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun loadBooks() {
        scope.launch {
            isLoading = true
            errorMessage = null
            val user = userRepository.getUserById(userId)
            username = user?.username ?: "User"

            // Try to get popular books from OpenLibrary API
            val result = bookRepository.getPopularBooks(subject = "bestseller", limit = 20)
            if (result.isSuccess) {
                recommendedBooks = result.getOrNull() ?: emptyList()
            } else {
                // Fallback to local database if API fails
                recommendedBooks = bookRepository.getAllBooks().take(10)
                if (recommendedBooks.isEmpty()) {
                    errorMessage = "Failed to load books: ${result.exceptionOrNull()?.localizedMessage}"
                } else {
                    snackbarHostState.showSnackbar(
                        message = "Showing offline content",
                        actionLabel = "OK",
                        duration = SnackbarDuration.Short
                    )
                }
            }

            isLoading = false
        }
    }

    LaunchedEffect(userId) {
        loadBooks()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = "home",
                onNavigateToHome = {},
                onNavigateToLibrary = onNavigateToLibrary,
                onNavigateToFavorites = onNavigateToFavorites,
                onNavigateToProfile = onNavigateToProfile
            )
        }
    ) { paddingValues ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorState(
                        message = errorMessage!!,
                        onRetry = { loadBooks() }
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    item {
                        // Header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = "Logo",
                                    modifier = Modifier.size(32.dp),
                                    colorFilter = ColorFilter.tint(GoldLibrary)
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = "AustenAlcott",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldLibrary
                                )
                            }

                            IconButton(onClick = { /* TODO: Notifications */ }) {
                                Icon(
                                    imageVector = Icons.Outlined.Notifications,
                                    contentDescription = "Notifications",
                                    tint = GoldLibrary
                                )
                            }
                        }
                    }

                    item {
                        // Welcome message
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.Baseline,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Welcome Back, ",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = username,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Italic,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    item {
                        // Search bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .clip(RoundedCornerShape(28.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable(onClick = onNavigateToSearch)
                                .padding(horizontal = 20.dp, vertical = 16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = "Search",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Search",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    item {
                        // Featured book card
                        if (recommendedBooks.isNotEmpty()) {
                            FeaturedBookCard(
                                book = recommendedBooks.first(),
                                onViewDetails = { onNavigateToBookDetails(recommendedBooks.first().id) },
                                onBookmark = { /* TODO: Toggle favorite */ },
                                onAdd = { /* TODO: Add to reading history */ },
                                modifier = Modifier.padding(horizontal = 20.dp)
                            )

                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }

                    if (recommendedBooks.size > 1) {
                        item {
                            Text(
                                text = "More Recommendations",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 20.dp)
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        item {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(recommendedBooks.drop(1)) { book ->
                                    BookCard(
                                        book = book,
                                        onViewDetails = { onNavigateToBookDetails(book.id) },
                                        onBookmark = { /* TODO: Toggle favorite */ },
                                        onAdd = { /* TODO: Add to reading history */ },
                                        modifier = Modifier.width(180.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
            }
        }
    }
}
