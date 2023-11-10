package dev.vengateshm.artbookappandroidtestingudemy.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dev.vengateshm.artbookappandroidtestingudemy.R
import dev.vengateshm.artbookappandroidtestingudemy.databinding.FragmentImageSearchBinding
import dev.vengateshm.artbookappandroidtestingudemy.util.Status
import dev.vengateshm.artbookappandroidtestingudemy.view.adapters.ImageListAdapter
import dev.vengateshm.artbookappandroidtestingudemy.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageSearchFragment @Inject constructor(val imageListAdapter: ImageListAdapter) :
    Fragment(R.layout.fragment_image_search) {

    private var fragmentBinding: FragmentImageSearchBinding? = null
    lateinit var viewModel: ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        val binding = FragmentImageSearchBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000L)
                editable?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        binding.rvImages.adapter = imageListAdapter
        binding.rvImages.layoutManager = GridLayoutManager(requireContext(), 3)
        imageListAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }
    }

    private fun subscribeToObservers() {
        viewModel.imageList.observe(viewLifecycleOwner) { resource ->

            when (resource.status) {
                Status.SUCCESS -> {
                    val urls = resource.data?.hits?.map { imageResult -> imageResult.previewURL }
                    imageListAdapter.images = urls ?: listOf()
                    fragmentBinding?.let { binding ->
                        binding.progressBar.isVisible = false
                    }
                }

                Status.FAILURE -> {
                    Toast.makeText(requireContext(), resource.message ?: "Error", Toast.LENGTH_LONG)
                        .show()
                    fragmentBinding?.let { binding ->
                        binding.progressBar.isVisible = false
                    }
                }

                Status.LOADING -> {
                    fragmentBinding?.let { binding ->
                        binding.progressBar.isVisible = true
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}