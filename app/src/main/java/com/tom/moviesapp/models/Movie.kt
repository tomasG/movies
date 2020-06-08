package com.tom.moviesapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int? = null,
    val poster_path: String? = null,
    val overview: String? = null,
    val title: String? = null,
    val popularity: Double? = 0.0,
    val vote_average: Double? = 0.0,
    val release_date: String? = null
) : Parcelable