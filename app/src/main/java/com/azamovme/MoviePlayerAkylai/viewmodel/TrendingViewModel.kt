package com.azamovme.MoviePlayerAkylai.viewmodel

import androidx.lifecycle.MutableLiveData
import com.azamovme.MoviePlayerAkylai.data.response.MovieInfo

interface TrendingViewModel {
    var trendingMovieList: MutableLiveData<ArrayList<MovieInfo>>

    fun loadTrendMovies()

}