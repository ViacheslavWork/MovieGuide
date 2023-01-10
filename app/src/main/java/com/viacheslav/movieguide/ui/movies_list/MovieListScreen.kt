package com.viacheslav.movieguide.ui.movies_list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.viacheslav.movieguide.R
import com.viacheslav.movieguide.ui.theme.MovieGuideTheme
import java.util.*

/**
 * Created by Viacheslav Avd on 09.01.2023
 */

fun getTestMovies(): List<MovieItemUi> {
    val movies = mutableListOf<MovieItemUi>()
    repeat(6) {
        movies.add(
            MovieItemUi(
                id = 1,
                title = "Avengers: End Game",
                ageLimit = 13,
                genres = "Action, Adventure, Drama, Action, Adventure, Drama",
                numberOfStars = 4,
                numberOfReviews = 125,
                duration = 137,
                isLiked = it % 2 == 0,
                poster = R.drawable.im_avengers_end_game
            )
        )
    }
    return movies
}

@Composable
fun MovieListScreen() {
    MovieGuideTheme {
        // A surface container using the 'background' color from the theme
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(14.dp)
        ) {
            Spacer(modifier = Modifier.height(44.dp))
            HeaderBlock()
            Spacer(modifier = Modifier.height(24.dp))
            ListBlock(getTestMovies())
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListBlock(testMovies: List<MovieItemUi>) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(testMovies) {
            MovieItem(movie = it)
        }
    }
}

@Composable
fun MovieItem(movie: MovieItemUi) {
    MovieGuideTheme {
        // A surface container using the 'background' color from the theme
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(8.dp))
                .clickable {  }
        ) {
            ImageBlock(movie = movie)
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = "${movie.duration} MIN",
                style = MaterialTheme.typography.labelLarge,
                fontSize = 8.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ImageBlock(movie: MovieItemUi) {
    MovieGuideTheme {
        // A surface container using the 'background' color from the theme
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 300.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Image(
                painter = painterResource(id = movie.poster),
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer { alpha = 0.99f }
                    .drawWithContent {
                        val colors = listOf(
                            Color.Black,
                            Color.Transparent
                        )
                        drawContent()
                        drawRect(
                            brush = Brush.verticalGradient(colors),
                            blendMode = BlendMode.DstIn
                        )
                    },
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .heightIn(min = 300.dp)
                    .padding(horizontal = 8.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        text = "${movie.ageLimit}+",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_like),
                        contentDescription = null,
                        modifier = Modifier
                            .width(16.dp)
                            .height(14.dp),
                        tint =
                        if (movie.isLiked) MaterialTheme.colorScheme.tertiary
                        else MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = movie.genres,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.tertiary
                )


                FilmRatingBlock(getTestMovies().first())
            }
        }
    }
}

@Composable
private fun FilmRatingBlock(movie: MovieItemUi) {
    Row(
        modifier = Modifier.padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) {
            Icon(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(8.dp),
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint =
                if (it <= movie.numberOfStars - 1) MaterialTheme.colorScheme.tertiary
                else MaterialTheme.colorScheme.onBackground
            )
        }
        Text(
            text = "${movie.numberOfReviews} reviews".uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.labelLarge,
            fontSize = 8.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun HeaderBlock() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.ic_target),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(R.string.movies_list),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ImageBlockPreview() {
    ImageBlock(getTestMovies().first())
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    HeaderBlock()
}

@Preview(showBackground = true)
@Composable
fun MoviesListPreview() {
    MovieListScreen()
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MovieItem(getTestMovies().first())
}
