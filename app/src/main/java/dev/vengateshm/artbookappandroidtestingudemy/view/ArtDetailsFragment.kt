package dev.vengateshm.artbookappandroidtestingudemy.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import dev.vengateshm.artbookappandroidtestingudemy.R
import dev.vengateshm.artbookappandroidtestingudemy.databinding.FragmentArtDetailsBinding
import dev.vengateshm.artbookappandroidtestingudemy.util.Status
import dev.vengateshm.artbookappandroidtestingudemy.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    private val glide: RequestManager
) : Fragment(R.layout.fragment_art_details) {

    private var fragmentBinding: FragmentArtDetailsBinding? = null
    lateinit var viewModel: ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]
        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.ivArtImage.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageSearchFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.btnSave.setOnClickListener {
            viewModel.createArt(
                binding.etName.text.toString(),
                binding.etArtist.text.toString(),
                binding.etYear.text.toString(),
            )
        }
    }

    private fun subscribeToObservers() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner) { imageUrl ->
            fragmentBinding?.let {
                glide.load(imageUrl).into(it.ivArtImage)
            }
        }

        viewModel.insertArtMessage.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetArtInsertMsg()
                }

                Status.FAILURE -> {
                    Toast.makeText(requireContext(), resource.message ?: "Error", Toast.LENGTH_LONG)
                        .show()
                    viewModel.resetArtInsertMsg()
                }

                Status.LOADING -> {

                }
            }
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}