package com.viacheslav.movieguide.ui

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.viacheslav.movieguide.MainActivity
import com.viacheslav.movieguide.PlayerActivity
import com.viacheslav.movieguide.ui.MovieGuideDestination.Details.ARG_MOVIE_ID
import com.viacheslav.movieguide.ui.details.DetailsScreen
import com.viacheslav.movieguide.ui.movies_list.MovieListScreen

@Composable
fun MovieGuideApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MovieGuideDestination.List.route,
        modifier = Modifier
    ) {
        composable(route = MovieGuideDestination.List.route) {
            MovieListScreen(onMovieClick = { movieId ->
                navController.navigate(route = "${MovieGuideDestination.Details.route}/$movieId")
            })
        }
        composable(
            route = "${MovieGuideDestination.Details.route}/{$ARG_MOVIE_ID}",
            arguments = listOf(navArgument(ARG_MOVIE_ID) { type = NavType.IntType })
        ) { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getInt(ARG_MOVIE_ID)
            val context = LocalContext.current
            movieId?.let { id ->
                DetailsScreen(movieId = id,
                    onBackButtonPressed = {
                        navController.popBackStack(MovieGuideDestination.List.route, false)
                    },
                    onPlayButtonPressed = {
                        val intent = Intent(context, PlayerActivity::class.java)
                        intent.putExtra(PlayerActivity.ARG_VIDEO_ID, it)
                        context.startActivity(intent)
                    })
            }
        }
    }
}