package dev.vengateshm.artbookappandroidtestingudemy.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import dev.vengateshm.artbookappandroidtestingudemy.repository.FakeArtRepository
import dev.vengateshm.artbookappandroidtestingudemy.util.MainDispatcherRule
import dev.vengateshm.artbookappandroidtestingudemy.util.Status
import dev.vengateshm.artbookappandroidtestingudemy.util.getOrAwaitValueTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup() {
        // Test doubles - creating a fake
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`() {
        viewModel.createArt("Mona Lisa", "Da Vinci", "")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.FAILURE)
    }

    @Test
    fun `insert art without name returns error`() {
        viewModel.createArt("", "Da Vinci", "1609")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.FAILURE)
    }

    @Test
    fun `insert art without artist name returns error`() {
        viewModel.createArt("Mona Lisa", "", "1609")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.FAILURE)
    }

    @Test
    fun `insert art returns success`() {
        viewModel.createArt("Mona Lisa", "Da Vinci", "1609")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }
}