package dev.vengateshm.artbookappandroidtestingudemy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vengateshm.artbookappandroidtestingudemy.model.ImageResponse
import dev.vengateshm.artbookappandroidtestingudemy.repository.ArtRepository
import dev.vengateshm.artbookappandroidtestingudemy.roomdb.Art
import dev.vengateshm.artbookappandroidtestingudemy.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val artRepository: ArtRepository
) : ViewModel() {

    // Art Fragment
    val artList = artRepository.getArts()

    // Image search fragment
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage

    // Art details fragment
    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage: LiveData<Resource<Art>>
        get() = insertArtMsg

    fun resetArtInsertMsg() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url: String) {
        selectedImage.postValue(url)
    }

    private fun insertArt(art: Art) {
        viewModelScope.launch {
            artRepository.insertArt(art)
        }
    }

    fun deleteArt(art: Art) {
        viewModelScope.launch {
            artRepository.deleteArt(art)
        }
    }

    fun searchForImage(query: String) {
        if (query.isBlank()) return

        images.value = Resource.loading(data = null)
        viewModelScope.launch {
            val response = artRepository.searchImage(query)
            images.value = response
        }
    }

    fun createArt(name: String, artistName: String, year: String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()) {
            insertArtMsg.postValue(
                Resource.failure(
                    data = null,
                    message = "Enter name, artist, year",
                )
            )
            return
        }

        val yearInt = try {
            year.toInt()
        } catch (e: Exception) {
            insertArtMsg.postValue(
                Resource.failure(
                    data = null,
                    message = "Year should be number",
                )
            )
            return
        }

        val art = Art(
            name = name,
            artistName = artistName,
            year = yearInt,
            imageUrl = selectedImage.value ?: ""
        )
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }
}