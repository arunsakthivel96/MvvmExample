package com.example.mvvmexample.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mvvmexample.data.api.TheMovieDbInterface
import com.example.mvvmexample.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(private val apiService : TheMovieDbInterface, private  val compositeDisposable: CompositeDisposable)  :
    DataSource.Factory<Int, Movie>() {

    val movieLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {

        val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        movieLiveDataSource.postValue(movieDataSource)

        return movieDataSource

    }
}