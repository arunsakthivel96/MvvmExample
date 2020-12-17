package com.example.mvvmexample.data.vo


import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    val id: Float,

    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String

)