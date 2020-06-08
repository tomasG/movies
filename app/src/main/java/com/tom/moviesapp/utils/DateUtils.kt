package com.tom.moviesapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.getDate(format: String = "dd.MM.yyyy"):String{
    val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(parser.parse(this)?: "")
}