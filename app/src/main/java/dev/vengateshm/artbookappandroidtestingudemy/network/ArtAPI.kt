package dev.vengateshm.artbookappandroidtestingudemy.network

import dev.vengateshm.artbookappandroidtestingudemy.model.ImageResponse
import dev.vengateshm.artbookappandroidtestingudemy.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtAPI {
    @GET("/api/")
    suspend fun searchImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = API_KEY
    ): Response<ImageResponse>
}