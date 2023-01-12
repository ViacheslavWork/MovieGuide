package com.viacheslav.movieguide.ui.movies_list

import com.viacheslav.movieguide.data.dto.GenreDto
import com.viacheslav.movieguide.data.dto.MovieListItemDto
import com.viacheslav.movieguide.data.retrofit.IMAGE_URL
import com.viacheslav.movieguide.toLine
import kotlin.math.roundToInt

/**
 * Created by Viacheslav Avd on 09.01.2023
 */
data class MovieItemUi(
    val id: Int,
    val title: String,
    val ageLimit: Int,
    val genres: String = "",
    val numberOfStars: Int,
    val numberOfReviews: Int,
    val isLiked: Boolean = false,
    val posterPath: String
) {
    companion object {
        fun fromMovieDto(
            movieListItemDto: MovieListItemDto,
            genres: List<GenreDto>
        ): MovieItemUi {
            return MovieItemUi(
                id = movieListItemDto.id,
                title = movieListItemDto.title,
                ageLimit = if (movieListItemDto.adult) 18 else 13,
                numberOfStars = (movieListItemDto.voteAverage / 2).roundToInt(),
                numberOfReviews = movieListItemDto.voteCount,
                genres = genres
                    .filter { movieListItemDto.genreIds.contains(it.id) }
                    .map { it.name }
                    .toLine(),
                isLiked = false,
                posterPath = IMAGE_URL.plus(movieListItemDto.posterPath)
            )
        }
    }
}
