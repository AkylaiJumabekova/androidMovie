package com.azamovme.MoviePlayerAkylai.repository

import com.azamovme.MoviePlayerAkylai.data.response.FullMovieData
import com.azamovme.MoviePlayerAkylai.data.response.MovieInfo
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    fun parseMovieDetailByHref(movieInfo: MovieInfo):Flow<Result<FullMovieData>>
}