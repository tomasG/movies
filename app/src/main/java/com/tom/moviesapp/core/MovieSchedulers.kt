package com.tom.moviesapp.core

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieSchedulers {
    fun mainThread(): Scheduler = AndroidSchedulers.mainThread()
    fun io() = Schedulers.io()
    fun single() = Schedulers.single()
}