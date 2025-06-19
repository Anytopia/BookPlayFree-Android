package com.zachnr.bookplayfree.dashboard.presentation.pages.library

import android.net.Uri
import com.zachnr.bookplayfree.dashboard.presentation.pages.library.state.LibraryEvent
import com.zachnr.bookplayfree.domain.model.BookDomain
import com.zachnr.bookplayfree.domain.model.DomainWrapper
import com.zachnr.bookplayfree.domain.repository.BookRepository
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.utils.utils.DispatcherProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LibraryViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LibraryViewModel
    private val fakeNavigator = mockk<Navigator>(relaxed = true)
    private val fakeBookRepository = mockk<BookRepository>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val dispatcherProvider = DispatcherProvider.test(testDispatcher)

        viewModel = LibraryViewModel(fakeNavigator, dispatcherProvider, fakeBookRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun loadBooksLoadingInitiation() = runTest {
        advanceUntilIdle() // advances coroutine queue, but NOT time delay
        advanceTimeBy(1000) // simulate delay(DUMMY_DELAY)
    }

    @Test
    fun `initial state triggers book loading and sets loading true then false`() = runTest {
        loadBooksLoadingInitiation()
        advanceUntilIdle() // now coroutine can complete
        assertEquals(false, viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.books.isEmpty())
    }

    @Test
    fun `handleEvents with SearchQueryTextChanged updates state`() = runTest {
        loadBooksLoadingInitiation()
        viewModel.handleEvents(LibraryEvent.SearchQueryTextChanged("android"))

        advanceUntilIdle()
        assertEquals("android", viewModel.state.value.searchQueryText)
    }

    @Test
    fun `handleEvents with SearchExpandedChanged updates state`() = runTest {
        loadBooksLoadingInitiation()
        viewModel.handleEvents(LibraryEvent.SearchExpandedChanged(true))

        advanceUntilIdle()
        assertTrue(viewModel.state.value.isSearchActive)
    }

    @Test
    fun `loadPdf with success sets loading false and updates books`() = runTest {
        loadBooksLoadingInitiation()
        val uri = mockk<Uri>()
        val dummyBooks = listOf(
            BookDomain(
                id = "1",
                title = "Test",
                author = "Author",
                thumbnail = null,
                uri = uri
            )
        )

        coEvery { fakeBookRepository.getAllPdfBooks(any()) } returns DomainWrapper.Success(dummyBooks)

        viewModel.handleEvents(LibraryEvent.LoadPdf(uri))

        advanceUntilIdle()

        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(dummyBooks, viewModel.state.value.books)
    }

    @Test
    fun `loadPdf with error sets error message`() = runTest {
        loadBooksLoadingInitiation()
        val uri = mockk<Uri>()
        coEvery { fakeBookRepository.getAllPdfBooks(any()) } returns DomainWrapper.Error(message = "PDF load failed")

        viewModel.handleEvents(LibraryEvent.LoadPdf(uri))

        advanceUntilIdle()

        assertEquals("PDF load failed", viewModel.state.value.errorMessage)
        assertEquals(false, viewModel.state.value.isLoading)
    }
}
