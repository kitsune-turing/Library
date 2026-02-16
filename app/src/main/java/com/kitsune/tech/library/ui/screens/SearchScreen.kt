package com.kitsune.tech.library.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kitsune.tech.library.data.database.Book
import com.kitsune.tech.library.data.database.BookRepository
import com.kitsune.tech.library.ui.components.BookListItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    userId: Int,
    bookRepository: BookRepository,
    onNavigateBack: () -> Unit,
    onNavigateToBookDetails: (Int) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<Book>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(searchQuery) {
        if (searchQuery.length >= 2) {
            scope.launch {
                searchResults = bookRepository.searchBooks(searchQuery)
            }
        } else {
            searchResults = emptyList()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search Books") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search by title, author, or genre...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search"
                    )
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (searchQuery.length < 2) {
                Text(
                    text = "Type at least 2 characters to search",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else if (searchResults.isEmpty()) {
                Text(
                    text = "No results found for \"$searchQuery\"",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(searchResults) { book ->
                        BookListItem(
                            book = book,
                            onClick = { onNavigateToBookDetails(book.id) }
                        )
                    }
                }
            }
        }
    }
}
