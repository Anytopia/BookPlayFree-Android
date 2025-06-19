package com.zachnr.bookplayfree.dashboard.presentation.pages.home

import com.zachnr.bookplayfree.domain.model.DeepSeekChatChoiceDomain
import com.zachnr.bookplayfree.domain.model.DeepSeekChatDomain
import com.zachnr.bookplayfree.domain.model.DeepSeekChatMessageDomain
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel
    private val fakeNavigator = mockk<Navigator>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(fakeNavigator)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state has default quote`() {
        val expectedQuote =
            "\"A reader lives a thousand lives before he dies. The man who never reads lives only one.\"— George R.R. Martin"
        assertEquals(expectedQuote, viewModel.state.value.quote)
    }

    @Test
    fun `updateQuote updates quote from domain`() = runTest {
        val expectedQuote = "Test quote for unit testing."
        val message = DeepSeekChatMessageDomain(content = expectedQuote)
        val choices = DeepSeekChatChoiceDomain(message = message)
        val domain = DeepSeekChatDomain(choices = listOf(choices))

        viewModel.updateQuote(domain)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(expectedQuote, viewModel.state.value.quote)
    }

    @Test
    fun `setOnQuerySearchChanged updates searchQueryText`() = runTest {
        viewModel.setOnQuerySearchChanged("Kotlin")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Kotlin", viewModel.state.value.searchQueryText)
    }

    @Test
    fun `setOnActiveStateSearchChanged updates isSearchActive`() = runTest {
        viewModel.setOnActiveStateSearchChanged(true)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.state.value.isSearchActive)
    }
}