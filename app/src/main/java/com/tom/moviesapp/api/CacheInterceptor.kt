package com.tom.moviesapp.api

import android.content.Context
import com.tom.moviesapp.utils.hasNetwork
import okhttp3.Interceptor
import okhttp3.Response


class CacheInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        request = when(context.hasNetwork()){
            true -> request.newBuilder().header("Cache-Control",
                "public, max-age=" + 5).build()
            false -> request.newBuilder().header("Cache-Control",
                "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
        }

        return chain.proceed(request)
    }
}