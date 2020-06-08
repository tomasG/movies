package com.tom.moviesapp.screens.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tom.moviesapp.core.DisposingViewModel
import com.tom.moviesapp.core.MovieSchedulers
import com.tom.moviesapp.models.Error
import com.tom.moviesapp.models.Movie
import com.tom.moviesapp.models.MovieListType
import com.tom.moviesapp.repositories.MoviesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

class MovieListViewModel(
    private val context: WeakReference<Context>,
    private val moviesRepository: MoviesRepository,
    private val schedulers: MovieSchedulers
) : DisposingViewModel() {

    private val moviesList = MutableLiveData<List<Movie>>()
    private val error = MutableLiveData<Error>()

    fun getError() = error

    fun getMovieList(): LiveData<List<Movie>> = moviesList

    fun loadMoviesList(type: MovieListType, page: Int = 1) {
        val disposable = moviesRepository.getMovies(type, page)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribe({ moviesResponse ->
                moviesResponse?.let {
                    moviesList.value = it.results
                }
            }, { throwable ->
                error.value = Error.getError(context, throwable)
            })
        addDisposable(disposable)
    }

    fun searchMovies(search: String) {
        if (search.trim().length > 1) {
            val disposable = moviesRepository.findMovies(search)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe({ moviesResponse ->
                    moviesResponse?.let {
                        moviesList.value = it.results
                    }
                }, { throwable ->
                    error.value = Error.getError(context, throwable)
                })
            addDisposable(disposable)
        }
    }

}