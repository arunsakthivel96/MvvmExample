package com.example.mvvmexample.UI.single_page_detail

import androidx.lifecycle.LiveData
import com.example.mvvmexample.data.api.TheMovieDbInterface
import com.example.mvvmexample.data.repository.MovieDetailsNetworkDataSource
import com.example.mvvmexample.data.repository.NetworkState
import com.example.mvvmexample.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private  val apiservice:TheMovieDbInterface) {

    lateinit var movieNetworkDataSource : MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(compositeDisposable : CompositeDisposable, movieId:Float) :LiveData<MovieDetails>{
        movieNetworkDataSource = MovieDetailsNetworkDataSource(apiservice,compositeDisposable)

        movieNetworkDataSource.fetchMovieDetails(movieId)

        return movieNetworkDataSource.downloadedmoviedetails
    }
    fun getMovieDetailsNetworkState():LiveData<NetworkState>{

        return  movieNetworkDataSource.networkState
    }

}