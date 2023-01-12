package com.viacheslav.movieguide.ui.details

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.viacheslav.movieguide.R
import com.viacheslav.movieguide.ui.theme.MovieGuideTheme
import java.util.*

/**
 * Created by Viacheslav Avd on 09.01.2023
 */

const val ARG_MOVIE_ID = "ARG_MOVIE_ID"

private val testCastList = listOf(
    R.drawable.im_downey to R.string.downey,
    R.drawable.im_evans to R.string.evans,
    R.drawable.im_hemsworth to R.string.hemsworth,
    R.drawable.im_ruffalo to R.string.ruffalo,
).map { DrawableStringPair(it.first, it.second) }

private data class DrawableStringPair(
    @DrawableRes val drawable: Int, @StringRes val text: Int
)

@Composable
fun DetailsScreen(
    movieId: Int,
    onBackButtonPressed: () -> Unit = {}
) {
    Log.d("TAG", "movie id: $movieId")
    val viewModel: MoviesDetailsViewModel = viewModel()
    viewModel.getMovie(movieId)
    val movie by viewModel.movie.collectAsState()
    movie?.let { movieDetails ->
        MovieGuideTheme {
            // A surface container using the 'background' color from the theme
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colorScheme.background)
            ) {
                AsyncImage(
                    model = movieDetails.posterPath,
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
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Spacer(modifier = Modifier.height(59.dp))
                    BackButton(onBackButtonPressed)
                    Spacer(modifier = Modifier.height(160.dp))
                    Text(
                        text = stringResource(id = R.string.age_limit, movieDetails.ageLimit),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = movieDetails.title,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = movieDetails.genres,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    FilmRatingBlock(movieDetails)
                    StoryLineBlock(movieDetails.storyLine)
                    CastBlock(movieDetails.cast)
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
        }
    }
}

@Composable
fun BackButton(onBackButtonPressed: () -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onBackButtonPressed)
    ) {

        Icon(
            imageVector = Icons.Filled.KeyboardArrowLeft,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = stringResource(id = R.string.back_button_text),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 2.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun FilmRatingBlock(movie: DetailsUi) {
    Row(
        modifier = Modifier.padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) {
            Icon(
                modifier = Modifier.padding(end = 4.dp),
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
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun StoryLineBlock(storyLine: String) {
    Column(modifier = Modifier.padding(top = 24.dp)) {
        Text(
            text = stringResource(R.string.storyline),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = storyLine,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
fun CastBlock(cast: List<ActorUi>) {
    Text(
        text = stringResource(R.string.cast),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 24.dp),
        color = MaterialTheme.colorScheme.primary
    )
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(top = 7.dp)
    ) {
        items(cast) {
            ActorCard(photoPath = it.photoPath, name = it.name)
        }
    }
}

@Composable
fun ActorCard(
    photoPath: String,
    name: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.width(width = 80.dp),
    ) {

        AsyncImage(
            modifier = Modifier.size(80.dp),
            model = photoPath,
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.paddingFromBaseline(top = 6.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ActorPreview() {
//    ActorCard(drawable = R.drawable.im_evans, R.string.evans)
}

@Preview(showBackground = true)
@Composable
fun BackButtonPreview() {
    BackButton()
}

@Preview(showBackground = true)
@Composable
fun RatingBlockPreview() {
//    FilmRatingBlock()
}

@Preview(showBackground = true, backgroundColor = 0xFF191926)
@Composable
fun DetailsScreenPreview() {
    MovieGuideTheme {
        DetailsScreen(0)
    }
}