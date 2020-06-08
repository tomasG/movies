package com.tom.moviesapp.api

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestAPIFactory {

    companion object {
        private var BASE_URL: String = "https://api.themoviedb.org/3/"
        private var retrofit: Retrofit? = null

        fun generateRetrofitClient(context: Context): MoviesAPI? {
            if (retrofit == null) {
                val cacheSize = (5 * 1024 * 1024).toLong()
                val cache = Cache(context.cacheDir, cacheSize)

                val httpClient = OkHttpClient.Builder().apply {
                    cache(cache)
                    addInterceptor(CacheInterceptor(context))
                    addInterceptor(NetworkInterceptor())
                }
                retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

            }
            return retrofit?.create(MoviesAPI::class.java)
        }
    }
}