package com.azamovme.MoviePlayerAkylai.viewmodel.imp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azamovme.MoviePlayerAkylai.data.response.MovieInfo
import com.azamovme.MoviePlayerAkylai.repository.imp.FavoriteRepositoryImpl
import com.azamovme.MoviePlayerAkylai.viewmodel.FavoriteViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModelImpl @Inject constructor(val repositoryImpl: FavoriteRepositoryImpl) :
    FavoriteViewModel, ViewModel() {
    override var favoriteAnimeList: MutableLiveData<ArrayList<MovieInfo>> = MutableLiveData()

    override fun loadFav() {
       viewModelScope.launch {
           repositoryImpl.getFavoritesFromRoom().onEach {
               favoriteAnimeList.postValue(it as ArrayList<MovieInfo>?)
           }.launchIn(viewModelScope)
       }
    }
}