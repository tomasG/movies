package com.tom.moviesapp.repositories

import com.tom.moviesapp.api.MoviesAPI
import com.tom.moviesapp.models.MovieDetail
import com.tom.moviesapp.models.MovieListType
import com.tom.moviesapp.models.MoviesResponse
import io.reactivex.Single

class MoviesRepository(private val api: MoviesAPI) {

    fun getMovies(type: MovieListType, page: Int = 1): Single<MoviesResponse> {
        return when(type){
            MovieListType.POPULAR -> api.getPopularMovies(page)
            MovieListType.TOP_RATED -> api.getTopRatedMovies(page)
            else -> Single.just(null)
        }
    }

    fun findMovies(search: String): Single<MoviesResponse> = api.findMovies(search)

    fun getMovieDetail(movieId: Int): Single<MovieDetail> = api.getMovieDetail(movieId)

}