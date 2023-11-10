package dev.vengateshm.artbookappandroidtestingudemy.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.vengateshm.artbookappandroidtestingudemy.R
import dev.vengateshm.artbookappandroidtestingudemy.network.ArtAPI
import dev.vengateshm.artbookappandroidtestingudemy.repository.ArtRepository
import dev.vengateshm.artbookappandroidtestingudemy.repository.ArtRepositoryImpl
import dev.vengateshm.artbookappandroidtestingudemy.roomdb.ArtDao
import dev.vengateshm.artbookappandroidtestingudemy.roomdb.ArtDatabase
import dev.vengateshm.artbookappandroidtestingudemy.util.Util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ArtDatabase::class.java, "ArtBookDB").build()

    @Singleton
    @Provides
    fun providesArtDao(database: ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun providesArtAPI(): ArtAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ArtAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesArtRepository(artDao: ArtDao, artAPI: ArtAPI): ArtRepository {
        return ArtRepositoryImpl(artDao = artDao, artAPI = artAPI)
    }

    @Singleton
    @Provides
    fun providesGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_image_24)
        )
}