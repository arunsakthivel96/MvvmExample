package com.example.mvvmexample.data.api

import com.example.mvvmexample.data.vo.Movie
import com.example.mvvmexample.data.vo.MovieDetails
import com.example.mvvmexample.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbInterface {

    //https://api.themoviedb.org/3/movie/popular?api_key=b260de326e99e03f6bd2d2298953d272
    //https://api.themoviedb.org/3/movie/724989?api_key=b260de326e99e03f6bd2d2298953d272
    //https://api.themoviedb.org/3/
    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int) : Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Long): Single<MovieDetails>
}