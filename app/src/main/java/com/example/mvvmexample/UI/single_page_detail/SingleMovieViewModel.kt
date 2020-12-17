package com.example.mvvmexample.UI.single_page_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmexample.data.repository.NetworkState
import com.example.mvvmexample.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieRepository:MovieDetailsRepository,movieId:Float):ViewModel() {
    private  val compositeDisposable = CompositeDisposable()

    val movieDetail:LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }
    val networkStatus:LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}