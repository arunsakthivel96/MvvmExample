package com.example.mvvmexample.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_KEY = "b260de326e99e03f6bd2d2298953d272"
const val BASE_URL = "https://api.themoviedb.org/3/"

const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2"

const val FIRST_PAGE = 1
const val POST_PER_PAGE =20

//https://api.themoviedb.org/3/movie/popular?api_key=b260de326e99e03f6bd2d2298953d272
//https://api.themoviedb.org/3/movie/724989?api_key=b260de326e99e03f6bd2d2298953d272
//https://api.themoviedb.org/3/movie/337401?api_key=b260de326e99e03f6bd2d2298953d272
//https://api.themoviedb.org/3/

object TheMovieDBClient {

    fun getClient(): TheMovieDbInterface {
        val requestInterceptor = Interceptor { chain ->
            val url = chain.request().url().newBuilder().addQueryParameter(
                "api_key",
                API_KEY
            ).build()
            val request = chain.request().newBuilder().url(url).build()
            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder().addInterceptor(requestInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS).build()
        return Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(TheMovieDbInterface::class.java)
    }


}