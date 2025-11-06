package com.jhs.ui.main.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jhs.presentation.model.BookDetailUiModel
import com.jhs.ui.main.bookmark.BookmarkRoute
import com.jhs.ui.main.detail.BookDetailRoute
import com.jhs.ui.main.search.SearchRoute


@Composable
fun SearchApp() {
    val nav = rememberNavController()

    Scaffold(
        containerColor = Color.LightGray,
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = { BottomBar(nav) }
    ) { inner ->
        NavHost(
            navController = nav,
            startDestination = Destination.Search.route,
            modifier = Modifier.padding(inner)
        ) {
            composable(Destination.Search.route) {
                SearchRoute(
                    onNavigateDetail = { book ->
                        nav.currentBackStackEntry?.savedStateHandle?.set("book", book)
                        nav.navigate(Destination.Detail.route)
                    }
                )
            }
            composable(Destination.Favorites.route) {
                BookmarkRoute(
                    onNavigateDetail = { book ->
                        nav.currentBackStackEntry?.savedStateHandle?.set("book", book)
                        nav.navigate(Destination.Detail.route)
                    }
                )
            }

            composable(Destination.Detail.route) {
                val book = nav.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<BookDetailUiModel>("book")

                if (book != null) {
                    BookDetailRoute(
                        item = book,
                        onBack = { nav.popBackStack() }
                    )
                }

            }
        }
    }
}


@Composable
private fun BottomBar(nav: NavHostController) {
    val backStack by nav.currentBackStackEntryAsState()
    val current = backStack?.destination

    NavigationBar {
        listOf(Destination.Search, Destination.Favorites).forEach { d ->
            val selected = current?.hierarchy?.any { it.route == d.route } == true
            NavigationBarItem(
                selected = selected,
                onClick = {
                    nav.navigate(d.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(nav.graph.startDestinationId) { saveState = true }
                    }
                },
                icon = {
                    if (d == Destination.Search) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "검색",
                            tint = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = "즐겨찾기",
                            tint = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    }
                },
                label = { Text(d.route) }
            )
        }
    }
}


@Composable
@Preview
fun PreApp() {
    SearchApp()
}