package com.viacheslav.movieguide.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.viacheslav.movieguide.ui.details.ARG_MOVIE_ID
import com.viacheslav.movieguide.ui.details.DetailsScreen
import com.viacheslav.movieguide.ui.movies_list.MovieListScreen

/**
 * Created by Viacheslav Avd on 11.01.2023
 */

enum class MovieGuideScreen {
    List,
    Details
}

@Composable
fun MovieGuideApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MovieGuideScreen.List.name,
        modifier = Modifier
    ) {
        composable(route = MovieGuideScreen.List.name) {
            MovieListScreen(onMovieClick = { movieId ->
                navController.navigate(route = "${MovieGuideScreen.Details.name}/$movieId")
            })
        }
        composable(
            route = "${MovieGuideScreen.Details.name}/{${ARG_MOVIE_ID}}",
            arguments = listOf(navArgument(ARG_MOVIE_ID) { type = NavType.IntType })
        ) { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getInt(ARG_MOVIE_ID)
            movieId?.let { id ->
                DetailsScreen(
                    movieId = id,
                    onBackButtonPressed = { navController.popBackStack() }
                )
            }
        }
    }
}