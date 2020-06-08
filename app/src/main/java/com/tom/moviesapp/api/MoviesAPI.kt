package com.tom.moviesapp.api

import com.tom.moviesapp.models.MovieDetail
import com.tom.moviesapp.models.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int = 1) :Single<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int = 1) :Single<MoviesResponse>

    @GET("search/movie")
    fun findMovies(@Query("query") query: String = "") :Single<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movieId: Int = 0,
    @Query("append_to_response") append: String = "videos") :Single<MovieDetail>

}