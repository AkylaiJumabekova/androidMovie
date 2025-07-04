package com.azamovme.MoviePlayerAkylai.viewmodel.imp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azamovme.MoviePlayerAkylai.data.response.MovieInfo
import com.azamovme.MoviePlayerAkylai.repository.imp.HomeRepositoryImpl
import com.azamovme.MoviePlayerAkylai.repository.imp.SearchRepositoryImpl
import com.azamovme.MoviePlayerAkylai.utils.Resource
import com.azamovme.MoviePlayerAkylai.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchViewModelImpl : SearchViewModel, ViewModel() {

    private val repository = SearchRepositoryImpl()
    private val homeRepository = HomeRepositoryImpl()
    var lastPage: Int = 1
    var lastPageLast: Int = 1
    var isSearch = false
    var pagingData: ArrayList<MovieInfo> = arrayListOf()
    var pagingDataLast: ArrayList<MovieInfo> = arrayListOf()
    override val searchData: MutableLiveData<Resource<ArrayList<MovieInfo>>> = MutableLiveData()
    override var loadPagingData: MutableLiveData<Resource<ArrayList<MovieInfo>>> = MutableLiveData()
    override var loadLastPagingData: MutableLiveData<Resource<ArrayList<MovieInfo>>> =
        MutableLiveData()
    init{
    loadNextPage(1)

}
    override fun searchMovie(query: String) {
        searchData.postValue(Resource.Loading)
        repository.searchMovies(query).onEach {
            it.onSuccess {
                searchData.postValue(Resource.Success(it))
            }
            it.onFailure {
                searchData.postValue(Resource.Error(it))
            }
        }.launchIn(viewModelScope)
    }


    override fun loadNextPage(page: Int) {
        lastPage = page
        loadPagingData.postValue(Resource.Loading)
        homeRepository.loadNextPage(page).onEach {
            it.onSuccess {
                loadPagingData.postValue(Resource.Success(it))
            }
            it.onFailure {
                println(it.message)
                loadPagingData.postValue(Resource.Error(it))
            }
        }.launchIn(viewModelScope)
    }

    override fun loadNextPageLast(page: Int) {
        lastPageLast = page
        loadLastPagingData.postValue(Resource.Loading)
        homeRepository.getLastPagination(page).onEach {
            it.onSuccess {
                println(it.toString())
                loadLastPagingData.postValue(Resource.Success(it))
            }
            it.onFailure {
                println(it.message)
                loadLastPagingData.postValue(Resource.Error(it))
            }
        }.launchIn(viewModelScope)
    }

}