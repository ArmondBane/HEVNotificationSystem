package com.example.hevnotificationsystem.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.hevnotificationsystem.util.rememberAnimatedNavController
import com.example.main.ui.MainScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
@Composable
fun Navigation(exit: () -> Unit) {
    BoxWithConstraints {
        val navController = rememberAnimatedNavController()
        AnimatedNavHost(
            navController = navController,
            startDestination = ScreensInspector.Main.route,
            builder = {
                mainScreenSetup(
                    exit = exit,
                    navController = navController,
                    width = constraints.maxWidth / 2,
                )
            }
        )
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.mainScreenSetup(
    exit: () -> Unit,
    navController: NavController,
    width: Int,
) {
    composable(
        route = ScreensInspector.Main.route,
        exitTransition = {_, _ ->
            slideOutHorizontally(
                targetOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutLinearInEasing
                )
            ) +
            fadeOut(
                animationSpec = tween(300)
            )
        },
        popEnterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) +
            fadeIn(
                animationSpec = tween(300)
            )
        },
    ) {
        MainScreen(exit)
    }
}