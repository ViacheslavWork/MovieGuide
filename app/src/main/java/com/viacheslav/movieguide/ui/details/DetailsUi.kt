package com.viacheslav.movieguide.ui.details

import com.viacheslav.movieguide.data.dto.CastItem
import com.viacheslav.movieguide.data.dto.MovieDetailsDto
import com.viacheslav.movieguide.data.retrofit.IMAGE_URL
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
    val posterPath: String
) {
    companion object {
        fun fromDto(dto: MovieDetailsDto) =
            DetailsUi(
                id = dto.id,
                ageLimit = if (dto.adult) 18 else 13,
                title = dto.title,
                genres = dto.genres.let {
                    val strBuilder = StringBuilder()
                    it.forEachIndexed { index, genre ->
                        strBuilder.append(genre.name)
                        if (index != it.size - 1) strBuilder.append(", ")
                    }
                    strBuilder.toString()
                },
                numberOfStars = (dto.voteAverage / 2).roundToInt(),
                numberOfReviews = dto.voteCount,
                posterPath = IMAGE_URL.plus(dto.backdropPath),
                storyLine = dto.overview ?: "",
            )
    }
}

data class ActorUi(val name: String, val photoPath: String) {
    companion object {
        fun fromCastItemDto(dto: CastItem) = ActorUi(
            name = dto.name,
            photoPath = IMAGE_URL.plus(dto.profilePath)
        )
    }
}
