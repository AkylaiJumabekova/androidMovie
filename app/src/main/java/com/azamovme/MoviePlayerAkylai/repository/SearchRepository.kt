package com.azamovme.MoviePlayerAkylai.repository

import com.azamovme.MoviePlayerAkylai.data.response.MovieInfo
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchMovies(query: String): Flow<Result<ArrayList<MovieInfo>>>
}