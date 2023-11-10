package dev.vengateshm.artbookappandroidtestingudemy.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.vengateshm.artbookappandroidtestingudemy.util.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTestWIthHilt {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var artDatabase: ArtDatabase
    private lateinit var artDao: ArtDao

    @Before
    fun setUp() {
        hiltRule.inject()
        artDao = artDatabase.artDao()
    }

    @Test
    fun test_insert_art() = runTest {
        val art = Art(
            id = 1,
            name = "Mona Lisa",
            artistName = "Da Vinci",
            year = 1609,
            imageUrl = "test.png"
        )
        artDao.insertArt(art)

        val arts = artDao.observeArts().getOrAwaitValueTest()
        Truth.assertThat(arts).isNotEmpty()
        Truth.assertThat(arts).contains(art)
    }

    @Test
    fun test_delete_art() = runTest {
        val art = Art(
            id = 1,
            name = "Mona Lisa",
            artistName = "Da Vinci",
            year = 1609,
            imageUrl = "test.png"
        )
        artDao.insertArt(art)
        artDao.deleteArt(art)

        val arts = artDao.observeArts().getOrAwaitValueTest()
        Truth.assertThat(arts).isEmpty()
        Truth.assertThat(arts).doesNotContain(art)
    }

    @After
    fun tearDown() {
        artDatabase.close()
    }
}