package dev.vengateshm.artbookappandroidtestingudemy.repository

import androidx.lifecycle.LiveData
import dev.vengateshm.artbookappandroidtestingudemy.model.ImageResponse
import dev.vengateshm.artbookappandroidtestingudemy.network.ArtAPI
import dev.vengateshm.artbookappandroidtestingudemy.roomdb.Art
import dev.vengateshm.artbookappandroidtestingudemy.roomdb.ArtDao
import dev.vengateshm.artbookappandroidtestingudemy.util.Resource
import javax.inject.Inject

class ArtRepositoryImpl @Inject constructor(
    private val artDao: ArtDao,
    private val artAPI: ArtAPI
) : ArtRepository {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArts(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(query: String): Resource<ImageResponse> {
        return try {
            val response = artAPI.searchImage(query)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.failure("Error", null)
            } else {
                Resource.failure("Error", null)
            }
        } catch (e: Exception) {
            Resource.failure("No data!", null)
        }
    }
}