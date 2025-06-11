package com.azamovme.MoviePlayerAkylai.repository.imp

import com.azamovme.MoviePlayerAkylai.data.response.MovieInfo
import com.azamovme.MoviePlayerAkylai.repository.FavoriteRepository
import com.azamovme.MoviePlayerAkylai.room.FavRoomModel
import com.azamovme.MoviePlayerAkylai.room.LinkDao
import com.azamovme.MoviePlayerAkylai.utils.Constants.SOURCE
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(private val dao: LinkDao) : FavoriteRepository {
    override suspend fun getFavoritesFromRoom() = flow<List<MovieInfo>> {
        dao.getLinks(SOURCE).collect {animeList->
            val list = animeList.map {
                MovieInfo(
                    it.favGenre,
                    it.favRating,
                    it.nameString,
                    it.picLinkString,
                    it.linkString,
                    listOf("720p"),
                    it.favYear
                )
            }
            emit(list)

        }
    }

    override suspend fun checkFavoriteFromRoom(animeLink: String?, sourceName: String): Boolean {
        return dao.isItFav(animeLink?:"hh", sourceName)
    }

    override suspend fun removeFavFromRoom(animeLink: String?, sourceName: String) {
// if animeLink is null, bail out immediately
        animeLink ?: return

        // getFav should return a nullable FavRoomModel?
        val foundFav: FavRoomModel? = dao.getFav(animeLink, sourceName)
        if (foundFav != null) {
            dao.deleteOne(foundFav)
        }    }

    override suspend fun addFavToRoom(favRoomModel: FavRoomModel) {
        dao.insert(favRoomModel)
    }
}