package dev.vengateshm.artbookappandroidtestingudemy.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.vengateshm.artbookappandroidtestingudemy.R
import dev.vengateshm.artbookappandroidtestingudemy.launchFragmentInHiltContainer
import dev.vengateshm.artbookappandroidtestingudemy.repository.FakeArtRepositoryAndroid
import dev.vengateshm.artbookappandroidtestingudemy.util.getOrAwaitValueTest
import dev.vengateshm.artbookappandroidtestingudemy.view.adapters.ImageListAdapter
import dev.vengateshm.artbookappandroidtestingudemy.viewmodel.ArtViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImageSearchFragmentTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun test_selected_image() {
        val navController = mock(NavController::class.java)
        val artViewModel = ArtViewModel(FakeArtRepositoryAndroid())
        val selectedImage = "test.png"
        launchFragmentInHiltContainer<ImageSearchFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = artViewModel
            imageListAdapter.images = listOf(selectedImage)
        }

        onView(withId(R.id.rvImages)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageListAdapter.ImageListItemViewHolder>(
                0, click()
            )
        )

        verify(navController).popBackStack()

        assertThat(artViewModel.selectedImageUrl.getOrAwaitValueTest()).isEqualTo(selectedImage)
    }

    @After
    fun tearDown() {

    }
}