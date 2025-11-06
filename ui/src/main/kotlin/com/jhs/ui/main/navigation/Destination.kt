package com.jhs.ui.main.navigation

sealed class Destination(val route: String, val displayName: String) {
    data object Search : Destination("search", "검색")
    data object Favorites : Destination("favorites", "즐겨찾기")
    data object Detail : Destination("detail", "상세화면")
}
