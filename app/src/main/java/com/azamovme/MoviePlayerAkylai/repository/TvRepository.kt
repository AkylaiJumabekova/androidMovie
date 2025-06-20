package com.azamovme.MoviePlayerAkylai.repository

import com.azamovme.MoviePlayerAkylai.data.Movie
import kotlinx.coroutines.flow.Flow

interface TvRepository {
    fun getTv(): Flow<Result<ArrayList<Movie>>>
    fun getNextTvPage(page:Int): Flow<Result<ArrayList<Movie>>>

  suspend  fun getTvFullDataByHref(href: String): String
}