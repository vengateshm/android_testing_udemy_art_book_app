package dev.vengateshm.artbookappandroidtestingudemy.repository

import androidx.lifecycle.LiveData
import dev.vengateshm.artbookappandroidtestingudemy.model.ImageResponse
import dev.vengateshm.artbookappandroidtestingudemy.roomdb.Art
import dev.vengateshm.artbookappandroidtestingudemy.util.Resource

interface ArtRepository {
    suspend fun insertArt(art: Art)
    suspend fun deleteArt(art: Art)
    fun getArts(): LiveData<List<Art>>
    suspend fun searchImage(query: String): Resource<ImageResponse>
}