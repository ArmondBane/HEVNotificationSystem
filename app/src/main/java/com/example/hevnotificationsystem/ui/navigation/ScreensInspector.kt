package com.example.hevnotificationsystem.ui.navigation

import androidx.navigation.compose.NamedNavArgument

sealed class ScreensInspector(
    val route: String,
    val arguments: List<NamedNavArgument>? = null
) {
    object Main: ScreensInspector("Main")
}