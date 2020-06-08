package com.tom.moviesapp.models

data class MovieDetail(
    val id: Int?,
    val poster_path: String?,
    val overview: String?,
    val title: String?,
    val popularity: Double?,
    val video: Boolean?,
    val vote_average: Double?,
    val release_date: String?,
    val genres: List<Genre>?,
    @Transient var genresText: String,
    val videos: VideosResults?
)

data class VideosResults(val results: List<MovieVideo>)

data class MovieVideo(
    val id: String,
    val key: String?,
    val site: String?
)

data class Genre(
    val id: Int,
    val name: String
)