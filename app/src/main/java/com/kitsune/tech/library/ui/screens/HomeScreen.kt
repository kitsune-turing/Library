package com.kitsune.tech.library.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kitsune.tech.library.R
import com.kitsune.tech.library.data.database.Book
import com.kitsune.tech.library.data.database.BookRepository
import com.kitsune.tech.library.data.database.UserRepository
import com.kitsune.tech.library.ui.components.BottomNavigationBar
import com.kitsune.tech.library.ui.components.BookCard
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
    val scope = rememberCoroutineScope()

    LaunchedEffect(userId) {
        scope.launch {
            val user = userRepository.getUserById(userId)
            username = user?.username ?: "User"
            recommendedBooks = bookRepository.getAllBooks().take(10)
            isLoading = false
        }
    }

    Scaffold(
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(40.dp),
                            colorFilter = ColorFilter.tint(GoldLibrary)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "LusternIcon",
                            style = MaterialTheme.typography.titleLarge,
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

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Welcome Back,",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = username,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onNavigateToSearch),
                    placeholder = { Text("Search") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search"
                        )
                    },
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "BASED ON YOUR LAST READ",
                    style = MaterialTheme.typography.labelLarge,
                    color = GoldLibrary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(recommendedBooks) { book ->
                        BookCard(
                            book = book,
                            onViewDetails = { onNavigateToBookDetails(book.id) },
                            onBookmark = { /* TODO: Toggle favorite */ },
                            onAdd = { /* TODO: Add to reading history */ }
                        )
                    }
                }
            }
        }
    }
}
