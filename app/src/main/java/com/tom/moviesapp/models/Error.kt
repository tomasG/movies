package com.tom.moviesapp.models

import android.content.Context
import com.tom.moviesapp.R
import retrofit2.HttpException
import java.lang.ref.WeakReference

class Error(
    val message: String = "",
    val code: Int = 0
) {

    companion object {
        fun getError(weakContext: WeakReference<Context>, throwable: Throwable): Error {
            val context = weakContext.get()
            return when (throwable) {
                is HttpException -> Error(
                    context?.getString(R.string.generic_error)
                    ?: "",
                    throwable.code()
                )
                else -> Error(
                    context?.getString(R.string.generic_error)
                    ?: "",
                    400
                )
            }
        }
    }
}