package com.example.mvvmexample.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmexample.data.api.TheMovieDbInterface
import com.example.mvvmexample.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import kotlin.math.roundToInt
import kotlin.math.roundToLong


class MovieDetailsNetworkDataSource(
    private val apiService: TheMovieDbInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkstate = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> get() = _networkstate

    private val _downloadedmoviedetails = MutableLiveData<MovieDetails>()
    val downloadedmoviedetails: LiveData<MovieDetails> get() = _downloadedmoviedetails

    @SuppressLint("LongLogTag")
    fun fetchMovieDetails(movieId: Float) {
        _networkstate.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(id = movieId.roundToLong()).subscribeOn(Schedulers.io()).subscribe({
                    _downloadedmoviedetails.postValue(it)
                    _networkstate.postValue(NetworkState.LOADED)
                    Log.e("Movie Details Data Source 0","${movieId} - ${movieId.roundToInt()}" )
                }, {
                    _networkstate.postValue(NetworkState.ERROR)
                    Log.e("Movie Details Data Source 1 - ${movieId.roundToInt()}", it.message)
                })
            )
        } catch (e: Exception) {
            Log.e("Movie Details Data Source", e.message)
        }
    }
}



