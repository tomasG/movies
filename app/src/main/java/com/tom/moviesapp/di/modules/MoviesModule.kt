package com.tom.moviesapp.di.modules

import com.tom.moviesapp.api.RestAPIFactory
import com.tom.moviesapp.core.MovieSchedulers
import com.tom.moviesapp.repositories.MoviesRepository
import com.tom.moviesapp.screens.detail.MovieDetailViewModel
import com.tom.moviesapp.screens.home.MovieListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import java.lang.ref.WeakReference


/**
 * Dependency injection
 */
val movieModule: Module = module {
    viewModel { MovieListViewModel(WeakReference(androidContext()), get(), get()) }
    viewModel { MovieDetailViewModel(WeakReference(androidContext()), get(), get()) }
    single { RestAPIFactory.generateRetrofitClient(androidContext()) }
    single { MoviesRepository(get()) }
    single { MovieSchedulers() }
}