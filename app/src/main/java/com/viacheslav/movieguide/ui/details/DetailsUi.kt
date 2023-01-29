package com.viacheslav.movieguide.ui.details

import com.viacheslav.movieguide.data.dto.CastItemDto
import com.viacheslav.movieguide.data.dto.MovieDetailsDto
import com.viacheslav.movieguide.di.MOVIES_IMAGE_URL
//import com.viacheslav.movieguide.data.retrofit.IMAGE_URL
import com.viacheslav.movieguide.ui.toLine
import kotlin.math.roundToInt

/**
 * Created by Viacheslav Avd on 12.01.2023
 */
data class DetailsUi(
    val id: Int,
    val ageLimit: Int,
    val title: String,
    val genres: String,
    val numberOfStars: Int,
    val numberOfReviews: Int,
    val storyLine: String,
    val cast: List<ActorUi> = emptyList(),
    val picturePath: String,
    val trailerYouTubeId: String? = null
) {
    companion object {
        fun fromDto(
            movieDetailsDto: MovieDetailsDto,
            castDto: List<CastItemDto>,
            trailerId: String?
        ) =
            DetailsUi(
                id = movieDetailsDto.id,
                ageLimit = if (movieDetailsDto.adult) 18 else 13,
                title = movieDetailsDto.title,
                genres = movieDetailsDto.genres.map { it.name }.toLine(),
                numberOfStars = (movieDetailsDto.voteAverage / 2).roundToInt(),
                numberOfReviews = movieDetailsDto.voteCount,
                cast = castDto.map { ActorUi.fromCastItemDto(it) },
                picturePath = MOVIES_IMAGE_URL.plus(movieDetailsDto.backdropPath),
                storyLine = movieDetailsDto.overview ?: "",
                trailerYouTubeId = trailerId
            )
    }
}

data class ActorUi(val name: String, val photoPath: String) {
    companion object {
        fun fromCastItemDto(dto: CastItemDto) = ActorUi(
            name = dto.name,
            photoPath = MOVIES_IMAGE_URL.plus(dto.profilePath)
        )
    }
}
