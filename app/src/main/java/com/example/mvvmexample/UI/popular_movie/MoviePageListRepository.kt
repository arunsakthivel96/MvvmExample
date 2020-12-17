package com.example.mvvmexample.UI.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mvvmexample.data.api.POST_PER_PAGE
import com.example.mvvmexample.data.api.TheMovieDbInterface
import com.example.mvvmexample.data.repository.MovieDataSource
import com.example.mvvmexample.data.repository.MovieDataSourceFactory
import com.example.mvvmexample.data.repository.NetworkState
import com.example.mvvmexample.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePageListRepository(private val apiService: TheMovieDbInterface) {

    lateinit var moviePageList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        movieDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config: PagedList.Config =
            PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(
                POST_PER_PAGE
            ).build()

        moviePageList = LivePagedListBuilder(movieDataSourceFactory, config).build()

        return moviePageList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            movieDataSourceFactory.movieLiveDataSource, MovieDataSource::networkState
        )
    }
}