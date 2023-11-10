package dev.vengateshm.artbookappandroidtestingudemy.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import dev.vengateshm.artbookappandroidtestingudemy.view.adapters.ArtListAdapter
import dev.vengateshm.artbookappandroidtestingudemy.view.adapters.ImageListAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val artListAdapter: ArtListAdapter,
    private val imageListAdapter: ImageListAdapter,
    private val glide: RequestManager
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ArtFragment::class.java.name -> ArtFragment(artListAdapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            ImageSearchFragment::class.java.name -> ImageSearchFragment(imageListAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}