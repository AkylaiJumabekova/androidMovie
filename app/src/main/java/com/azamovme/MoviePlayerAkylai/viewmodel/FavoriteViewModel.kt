package com.azamovme.MoviePlayerAkylai.viewmodel

import androidx.lifecycle.MutableLiveData
import com.azamovme.MoviePlayerAkylai.data.response.MovieInfo

interface FavoriteViewModel {
    var favoriteAnimeList: MutableLiveData<ArrayList<MovieInfo>>

    fun loadFav()

}