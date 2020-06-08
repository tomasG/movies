package com.tom.moviesapp.screens.home

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.tom.moviesapp.api.MoviesAPI
import com.tom.moviesapp.core.MovieSchedulers
import com.tom.moviesapp.models.Movie
import com.tom.moviesapp.models.MovieListType
import com.tom.moviesapp.models.MoviesResponse
import com.tom.moviesapp.repositories.MoviesRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.lang.ref.WeakReference

@RunWith(JUnit4::class)
class MovieListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var api: MoviesAPI

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var schedulers: MovieSchedulers

    lateinit var viewModel: MovieListViewModel
    lateinit var moviesRepository: MoviesRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        moviesRepository = MoviesRepository(api)
        viewModel = MovieListViewModel(WeakReference(context), moviesRepository, schedulers)

        whenever(schedulers.io()).thenReturn(Schedulers.trampoline())
        whenever(schedulers.mainThread()).thenReturn(Schedulers.trampoline())

    }

    private fun getMoviesResponse(): MoviesResponse {
        return MoviesResponse(results = listOf(Movie(), Movie()))
    }

    @Test
    fun `loadMoviesList sending valid movieListType POPULAR`() {
        whenever(api.getPopularMovies(anyInt())).thenReturn(Single.just(getMoviesResponse()))

        viewModel.getMovieList().observeForever {}
        viewModel.loadMoviesList(MovieListType.POPULAR)

        assertEquals(2, viewModel.getMovieList().value?.size)
        verify(api, times(1)).getPopularMovies()

    }

    @Test
    fun `loadMoviesList sending valid movieListType TOP_RATED`() {
        whenever(api.getTopRatedMovies(anyInt())).thenReturn(Single.just(getMoviesResponse()))

        viewModel.getMovieList().observeForever {}
        viewModel.loadMoviesList(MovieListType.TOP_RATED)

        assertEquals(2, viewModel.getMovieList().value?.size)
        verify(api, times(1)).getTopRatedMovies()

    }

    @Test
    fun `loadMoviesList error send it`() {
        whenever(context.getString(anyInt())).thenReturn("String")
        whenever(api.getTopRatedMovies(anyInt())).thenReturn(Single.error(NullPointerException("error")))

        viewModel.getError().observeForever { }
        viewModel.loadMoviesList(MovieListType.TOP_RATED)

        assertEquals("String", viewModel.getError().value?.message)
        assertEquals(400, viewModel.getError().value?.code)
    }

    @Test
    fun `searchMovies empty string`() {

        viewModel.getMovieList().observeForever { }
        viewModel.searchMovies("  ")

        verify(api, never()).findMovies(anyString())
    }

    @Test
    fun `searchMovies invalid string size`() {

        viewModel.getMovieList().observeForever { }
        viewModel.searchMovies("a")

        verify(api, never()).findMovies(anyString())
    }

    @Test
    fun `searchMovies valid string size`() {
        whenever(api.findMovies(anyString())).thenReturn(Single.just(getMoviesResponse()))

        viewModel.getMovieList().observeForever { }
        viewModel.searchMovies("lord")

        verify(api, times(1)).findMovies(anyString())
        assertEquals(2, viewModel.getMovieList().value?.size)
    }

    @Test
    fun `searchMovies error send it`() {
        whenever(context.getString(anyInt())).thenReturn("String")
        whenever(api.findMovies(anyString())).thenReturn(Single.error(NullPointerException("error")))

        viewModel.getError().observeForever { }
        viewModel.searchMovies("lord")

        assertEquals("String", viewModel.getError().value?.message)
        assertEquals(400, viewModel.getError().value?.code)
    }

}
