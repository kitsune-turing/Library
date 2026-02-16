package com.kitsune.tech.library.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kitsune.tech.library.data.database.BookRepository
import com.kitsune.tech.library.data.database.FavoriteRepository
import com.kitsune.tech.library.data.database.UserRepository
import com.kitsune.tech.library.ui.screens.*

sealed class Screen(val route: String) {
    object Loading : Screen("loading")
    object Welcome : Screen("welcome")
    object SignIn : Screen("sign_in")
    object Register : Screen("register")
    object Home : Screen("home/{userId}") {
        fun createRoute(userId: Int) = "home/$userId"
    }
    object Search : Screen("search/{userId}") {
        fun createRoute(userId: Int) = "search/$userId"
    }
    object BookDetails : Screen("book_details/{userId}/{bookId}") {
        fun createRoute(userId: Int, bookId: Int) = "book_details/$userId/$bookId"
    }
    object Library : Screen("library/{userId}") {
        fun createRoute(userId: Int) = "library/$userId"
    }
    object Favorites : Screen("favorites/{userId}") {
        fun createRoute(userId: Int) = "favorites/$userId"
    }
    object Profile : Screen("profile/{userId}") {
        fun createRoute(userId: Int) = "profile/$userId"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    userRepository: UserRepository,
    bookRepository: BookRepository,
    favoriteRepository: FavoriteRepository
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Loading.route
    ) {
        composable(Screen.Loading.route) {
            LoadingScreen(
                onLoadingComplete = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Loading.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onSignInClick = {
                    navController.navigate(Screen.SignIn.route)
                },
                onCreateAccountClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.SignIn.route) {
            SignInScreen(
                userRepository = userRepository,
                onSignInSuccess = { userId ->
                    navController.navigate(Screen.Home.createRoute(userId)) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                userRepository = userRepository,
                onRegisterSuccess = { userId ->
                    navController.navigate(Screen.Home.createRoute(userId)) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Home.route,
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
            HomeScreen(
                userId = userId,
                userRepository = userRepository,
                bookRepository = bookRepository,
                onNavigateToSearch = {
                    navController.navigate(Screen.Search.createRoute(userId))
                },
                onNavigateToBookDetails = { bookId ->
                    navController.navigate(Screen.BookDetails.createRoute(userId, bookId))
                },
                onNavigateToLibrary = {
                    navController.navigate(Screen.Library.createRoute(userId))
                },
                onNavigateToFavorites = {
                    navController.navigate(Screen.Favorites.createRoute(userId))
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.createRoute(userId))
                }
            )
        }

        composable(
            route = Screen.Search.route,
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
            SearchScreen(
                userId = userId,
                bookRepository = bookRepository,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToBookDetails = { bookId ->
                    navController.navigate(Screen.BookDetails.createRoute(userId, bookId))
                }
            )
        }

        composable(
            route = Screen.BookDetails.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType },
                navArgument("bookId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: return@composable
            BookDetailsScreen(
                userId = userId,
                bookId = bookId,
                bookRepository = bookRepository,
                favoriteRepository = favoriteRepository,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Library.route,
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
            LibraryScreen(
                userId = userId,
                bookRepository = bookRepository,
                onNavigateToHome = {
                    navController.navigate(Screen.Home.createRoute(userId)) {
                        popUpTo(Screen.Home.createRoute(userId)) { inclusive = true }
                    }
                },
                onNavigateToBookDetails = { bookId ->
                    navController.navigate(Screen.BookDetails.createRoute(userId, bookId))
                },
                onNavigateToFavorites = {
                    navController.navigate(Screen.Favorites.createRoute(userId))
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.createRoute(userId))
                }
            )
        }

        composable(
            route = Screen.Favorites.route,
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
            FavoritesScreen(
                userId = userId,
                favoriteRepository = favoriteRepository,
                onNavigateToHome = {
                    navController.navigate(Screen.Home.createRoute(userId)) {
                        popUpTo(Screen.Home.createRoute(userId)) { inclusive = true }
                    }
                },
                onNavigateToLibrary = {
                    navController.navigate(Screen.Library.createRoute(userId))
                },
                onNavigateToBookDetails = { bookId ->
                    navController.navigate(Screen.BookDetails.createRoute(userId, bookId))
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.createRoute(userId))
                }
            )
        }

        composable(
            route = Screen.Profile.route,
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
            ProfileScreen(
                userId = userId,
                userRepository = userRepository,
                onNavigateToHome = {
                    navController.navigate(Screen.Home.createRoute(userId)) {
                        popUpTo(Screen.Home.createRoute(userId)) { inclusive = true }
                    }
                },
                onNavigateToLibrary = {
                    navController.navigate(Screen.Library.createRoute(userId))
                },
                onNavigateToFavorites = {
                    navController.navigate(Screen.Favorites.createRoute(userId))
                }
            )
        }
    }
}
