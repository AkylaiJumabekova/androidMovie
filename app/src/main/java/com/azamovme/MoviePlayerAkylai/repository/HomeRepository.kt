package com.azamovme.MoviePlayerAkylai.repository

import com.azamovme.MoviePlayerAkylai.data.response.MovieInfo

interface HomeRepository {
    fun loadNextPage(page:Int):kotlinx.coroutines.flow.Flow<Result<ArrayList<MovieInfo>>>
    fun loadPopularMovies():kotlinx.coroutines.flow.Flow<Result<ArrayList<MovieInfo>>>
    fun getNeedWatch():kotlinx.coroutines.flow.Flow<Result<ArrayList<MovieInfo>>>
    fun getLastPagination(page:Int):kotlinx.coroutines.flow.Flow<Result<ArrayList<MovieInfo>>>
    fun getLastNews():kotlinx.coroutines.flow.Flow<Result<ArrayList<MovieInfo>>>

}