package com.tom.moviesapp.screens.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.tom.moviesapp.core.DisposingViewModel
import com.tom.moviesapp.core.MovieSchedulers
import com.tom.moviesapp.models.Error
import com.tom.moviesapp.models.MovieDetail
import com.tom.moviesapp.repositories.MoviesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

class MovieDetailViewModel(
    private val context: WeakReference<Context>,
    private val repository: MoviesRepository,
    private val schedulers: MovieSchedulers
) : DisposingViewModel() {

    private val movie = MutableLiveData<MovieDetail>()
    private val error = MutableLiveData<Error>()

    fun getMovie() = movie

    fun getError() = error

    fun getMovieDetails(movieId: Int?) {
        Log.v("MOVIE_ID->", movieId.toString())
        movieId?.let {
            val disposable = repository.getMovieDetail(movieId)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe({ movieDetail ->
                    movieDetail?.let { detail ->
                        detail.genresText =
                            detail.genres?.joinToString(separator = ", ") { it.name } ?: ""
                        movie.value = detail
                    }
                }, { throwable ->
                    error.value = Error.getError(context, throwable)
                })
            addDisposable(disposable)
        }
    }
}