package com.kitsune.tech.library.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kitsune.tech.library.data.database.Book
import com.kitsune.tech.library.data.database.BookRepository
import com.kitsune.tech.library.data.database.FavoriteRepository
import com.kitsune.tech.library.ui.theme.GoldLibrary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(
    userId: Int,
    bookId: Int,
    bookRepository: BookRepository,
    favoriteRepository: FavoriteRepository,
    onNavigateBack: () -> Unit
) {
    var book by remember { mutableStateOf<Book?>(null) }
    var isFavorite by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(bookId) {
        scope.launch {
            book = bookRepository.getBookById(bookId)
            isFavorite = favoriteRepository.isFavorite(userId, bookId)
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                favoriteRepository.toggleFavorite(userId, bookId)
                                isFavorite = !isFavorite
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = if (isFavorite) GoldLibrary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
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
        } else if (book == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Book not found")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (!book!!.coverImageUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = book!!.coverImageUrl,
                            contentDescription = "Cover for ${book!!.title}",
                            modifier = Modifier
                                .height(300.dp)
                                .width(200.dp),
                            contentScale = ContentScale.Fit
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.MenuBook,
                            contentDescription = "Book cover placeholder",
                            modifier = Modifier.size(120.dp),
                            tint = GoldLibrary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = book!!.title,
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "by ${book!!.author}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Genre",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = book!!.genre,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Column {
                        Text(
                            text = "Pages",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${book!!.totalPages}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Column {
                        Text(
                            text = "Published",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${book!!.publishedYear}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = book!!.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { /* TODO: Implement read functionality */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldLibrary
                    )
                ) {
                    Text("Read Now")
                }
            }
        }
    }
}
