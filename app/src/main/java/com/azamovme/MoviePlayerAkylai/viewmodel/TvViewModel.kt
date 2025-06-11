package com.azamovme.MoviePlayerAkylai.viewmodel

import androidx.lifecycle.MutableLiveData
import com.azamovme.MoviePlayerAkylai.data.Movie
import com.azamovme.MoviePlayerAkylai.tv.stv.response.RandomTvResponse
import com.azamovme.MoviePlayerAkylai.utils.Resource

interface TvViewModel {
    val tvList: MutableLiveData<Resource<ArrayList<Movie>>>
    val hrefData :MutableLiveData<String>
    val tvRandomList:MutableLiveData<Resource<RandomTvResponse>>
    fun loadTv()
    fun loadTvNextPage(page:Int)
    fun loadRandomTvById(id:Int)
    fun loadHrefData(movie:Movie)


}