package com.viacheslav.movieguide.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.viacheslav.movieguide.data.dto.GenreDto
import com.viacheslav.movieguide.data.retrofit.MoviesGuideApiService
import com.viacheslav.movieguide.ui.movies_list.MovieItemUi
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Viacheslav Avd on 16.01.2023
 */
const val MAX_PAGES = 1000

class PopularMoviesPagingSource @Inject constructor(
    private val apiService: MoviesGuideApiService,
    private val networkRequester: NetworkRequester
) : PagingSource<Int, MovieItemUi>() {
    private lateinit var allGenres: List<GenreDto>

    override fun getRefreshKey(state: PagingState<Int, MovieItemUi>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.nextKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItemUi> {
        val page: Int = params.key ?: 1
        val moviesResult = networkRequester.makeRequest { apiService.getPopularMovies(page) }
        if (!::allGenres.isInitialized) {
            val allGenresResult = networkRequester.makeRequest { apiService.getGenres() }
            if (allGenresResult is Result.Success)
                allGenres = allGenresResult.data.genres
        }
        return if (moviesResult is Result.Success && ::allGenres.isInitialized) {
            val popularMoviesDto =
                moviesResult.data.results.map { MovieItemUi.fromMovieDto(it, allGenres) }
            val nextKey = if (page == MAX_PAGES) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(data = popularMoviesDto, prevKey = prevKey, nextKey = nextKey)
        } else
            LoadResult.Error(IOException())
    }
}