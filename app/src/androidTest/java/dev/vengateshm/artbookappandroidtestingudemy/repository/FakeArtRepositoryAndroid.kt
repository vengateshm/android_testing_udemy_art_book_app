package dev.vengateshm.artbookappandroidtestingudemy.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.vengateshm.artbookappandroidtestingudemy.model.ImageResponse
import dev.vengateshm.artbookappandroidtestingudemy.roomdb.Art
import dev.vengateshm.artbookappandroidtestingudemy.util.Resource

class FakeArtRepositoryAndroid : ArtRepository {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>()

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getArts(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(query: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(), 0, 0))
    }

    private fun refreshData() {
        artsLiveData.postValue(arts)
    }
}