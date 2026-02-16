package com.kitsune.tech.library.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.kitsune.tech.library.ui.theme.GoldLibrary

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : BottomNavItem("home", Icons.Outlined.AutoStories, "Home")
    object Library : BottomNavItem("library", Icons.Outlined.LibraryBooks, "Library")
    object Favorites : BottomNavItem("favorites", Icons.Outlined.BookmarkBorder, "Favorites")
    object Profile : BottomNavItem("profile", Icons.Outlined.AccountCircle, "Profile")
}

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    onNavigateToHome: () -> Unit,
    onNavigateToLibrary: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Library,
        BottomNavItem.Favorites,
        BottomNavItem.Profile
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                selected = currentRoute.startsWith(item.route),
                onClick = {
                    when (item) {
                        BottomNavItem.Home -> onNavigateToHome()
                        BottomNavItem.Library -> onNavigateToLibrary()
                        BottomNavItem.Favorites -> onNavigateToFavorites()
                        BottomNavItem.Profile -> onNavigateToProfile()
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = GoldLibrary,
                    selectedTextColor = GoldLibrary,
                    indicatorColor = GoldLibrary.copy(alpha = 0.2f)
                )
            )
        }
    }
}
