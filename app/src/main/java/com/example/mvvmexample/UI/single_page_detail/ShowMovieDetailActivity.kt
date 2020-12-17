package com.example.mvvmexample.UI.single_page_detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.mvvmexample.R
import com.example.mvvmexample.data.api.POSTER_BASE_URL
import com.example.mvvmexample.data.api.TheMovieDBClient
import com.example.mvvmexample.data.api.TheMovieDbInterface
import com.example.mvvmexample.data.repository.NetworkState
import com.example.mvvmexample.data.vo.MovieDetails
import kotlinx.android.synthetic.main.activity_show_movie_detail.*
import java.lang.NumberFormatException
import java.text.NumberFormat
import java.util.*

class ShowMovieDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_movie_detail)

        val movieId: Float = intent.getFloatExtra("id",0.0f)

        val apiService: TheMovieDbInterface = TheMovieDBClient.getClient()
        movieDetailsRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetail.observe(this, Observer {
            bindUI(it)
        })
        viewModel.networkStatus.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
//            if (it.status.name == NetworkState.LOADING.toString()) {
//                imgv_thumbnail.visibility = View.GONE
//                ly_movie_detail.visibility = View.GONE
//                progress_bar.visibility = View.VISIBLE
//            }
//            if (it.status.name == NetworkState.LOADED.toString()) {
//                imgv_thumbnail.visibility = View.VISIBLE
//                ly_movie_detail.visibility = View.VISIBLE
//                progress_bar.visibility = View.GONE
//            }
//            if (it.status.name == "FAILED") {
//                imgv_thumbnail.visibility = View.GONE
//                ly_movie_detail.visibility = View.GONE
//                progress_bar.visibility = View.VISIBLE
//                txt_error.text = it.msg
//                txt_error.visibility = View.VISIBLE
//            }
            progress_bar.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })
    }

    private fun bindUI(it: MovieDetails) {
        txt_moviename.text = it.title
        txt_subtitle.text = it.tagline
        txt_release_date.text = it.releaseDate
        txt_rating.text = it.rating.toString()
        txt_runtime.text = it.runtime.toString() + " minutes"
        txt_overview.text = it.overview.toString()

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        txt_budget.text = formatCurrency.format(it.budget)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this).load(moviePosterURL).into(imgv_thumbnail)

    }

    private fun getViewModel(movieId: Float): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SingleMovieViewModel(movieDetailsRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}