package com.tom.moviesapp.di

import android.app.Application
import com.tom.moviesapp.di.modules.movieModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoviesApplication)
            modules(arrayListOf(movieModule))
        }
    }
}