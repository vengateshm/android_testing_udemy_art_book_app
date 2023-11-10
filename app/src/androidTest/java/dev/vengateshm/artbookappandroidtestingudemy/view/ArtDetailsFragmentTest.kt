package dev.vengateshm.artbookappandroidtestingudemy.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.vengateshm.artbookappandroidtestingudemy.R
import dev.vengateshm.artbookappandroidtestingudemy.launchFragmentInHiltContainer
import dev.vengateshm.artbookappandroidtestingudemy.repository.FakeArtRepositoryAndroid
import dev.vengateshm.artbookappandroidtestingudemy.util.getOrAwaitValueTest
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
class ArtDetailsFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun test_navigation_from_art_detail_to_image_search_fragment() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.ivArtImage)).perform(click())

        verify(navController).navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageSearchFragment())
    }

    @Test
    fun test_on_back_pressed() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()
        verify(navController).popBackStack()
    }

    @Test
    fun test_save_functionality() {
        val artViewModel = ArtViewModel(FakeArtRepositoryAndroid())
        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ) {
            viewModel = artViewModel
        }
        onView(withId(R.id.etName)).perform(replaceText("Mona Lisa"))
        onView(withId(R.id.etArtist)).perform(replaceText("Da Vinci"))
        onView(withId(R.id.etYear)).perform(replaceText("1609"))
        onView(withId(R.id.btnSave)).perform(click())

        artViewModel.artList.getOrAwaitValueTest()
            .also {
                assertThat(it).isNotEmpty()
                assertThat(it[0].name).isEqualTo("Mona Lisa")
            }
    }

    @After
    fun tearDown() {

    }
}