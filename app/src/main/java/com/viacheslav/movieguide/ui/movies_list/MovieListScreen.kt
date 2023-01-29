package com.viacheslav.movieguide.ui.movies_list

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.viacheslav.movieguide.R
import com.viacheslav.movieguide.di.MOVIES_IMAGE_URL
import com.viacheslav.movieguide.ui.theme.MovieGuideTheme
import com.viacheslav.movieguide.ui.toLine
import kotlinx.coroutines.flow.flowOf
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by Viacheslav Avd on 09.01.2023
 */

@Composable
fun MovieListScreen(
    onMovieClick: (id: Int) -> Unit = {}, viewModel: MoviesListViewModel = hiltViewModel()
) {
    val moviesPages = viewModel.moviesFlow.collectAsLazyPagingItems()
    Screen(moviesPages, onMovieClick)
}

@Composable
private fun Screen(
    moviesPages: LazyPagingItems<MovieItemUi>, onMovieClick: (id: Int) -> Unit
) {
    MovieGuideTheme {
        // A surface container using the 'background' color from the theme
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 14.dp)
        ) {
            Spacer(modifier = Modifier.height(44.dp))
            HeaderBlock()
            Spacer(modifier = Modifier.height(24.dp))
            ListBlock(onMovieClick, moviesPages)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListBlock(
    onMovieClick: (id: Int) -> Unit,
    moviesPages: LazyPagingItems<MovieItemUi>,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 14.dp)
    ) {
        items(moviesPages.itemCount) { index ->
            moviesPages[index]?.let { movieItemUi ->
                MovieItem(movie = movieItemUi, onClick = { onMovieClick(movieItemUi.id) })
            }
        }
    }
}

@Composable
fun MovieItem(movie: MovieItemUi, onClick: (id: Int) -> Unit) {
    MovieGuideTheme {
        // A surface container using the 'background' color from the theme
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(8.dp))
            .clickable { onClick(movie.id) }) {
            ImageBlock(movie = movie)
        }
    }
}

@Composable
private fun ImageBlock(movie: MovieItemUi) {
    Log.d("TAG", "ImageBlock: $movie")
    MovieGuideTheme {
        // A surface container using the 'background' color from the theme
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            AsyncImage(
                model = movie.posterPath,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .graphicsLayer { alpha = 0.99f }
                    .drawWithContent {
                        val colors = listOf(Color.Black, Color.Transparent)
                        drawContent()
                        drawRect(
                            brush = Brush.verticalGradient(colors), blendMode = BlendMode.DstIn
                        )
                    },
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 10.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    HeadBlock(movie)
                }
                Text(
                    text = movie.genres,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
                FilmRatingBlock(movie)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun HeadBlock(movie: MovieItemUi) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.age_limit, movie.ageLimit),
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
            tint = if (movie.isLiked) MaterialTheme.colorScheme.tertiary
            else MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun FilmRatingBlock(movie: MovieItemUi) {
    Row(
        modifier = Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) {
            Icon(
                modifier = Modifier
                    .padding(end = 2.dp)
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
    Screen(moviesPages = flowOf(
        PagingData.from(
            listOf(
                MovieItemUi(
                    id = 1,
                    isLiked = false,
                    ageLimit = 13,
                    title = "Avengers: End Game",
                    genres = "Action, Adventure, Fantasy",
                    numberOfStars = 4,
                    numberOfReviews = 100500,
                    posterPath = MOVIES_IMAGE_URL.plus("/rugyJdeoJm7cSJL1q4jBpTNbxyU.jpg")
                )
            )
        )
    ).collectAsLazyPagingItems(), {})
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MovieItem(getTestMovies().first(), {})
}
