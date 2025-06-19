package com.zachnr.bookplayfree.presentation

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zachnr.bookplayfree.dashboard.presentation.pages.home.HomeScreen
import com.zachnr.bookplayfree.dashboard.presentation.pages.home.HomeState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_displaysTextsAndHandlesSearchQuery() {
        // Arrange: create a test state with a quote
        val testQuote = "Test quote for UI testing."
        var capturedQuery = ""
        val testState = HomeState(
            quote = testQuote,
            isSearchActive = false,
            searchQueryText = ""
        )

        // Act: set the HomeScreen content with test state and callbacks
        composeTestRule.setContent {
            HomeScreen(
                modifier = Modifier,
                state = testState,
                onQueryChange = { query -> capturedQuery = query },
                onExpandedChange = {}
            )
        }

        // Assert: verify static texts are displayed
        composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
        composeTestRule.onNodeWithText(testQuote).assertIsDisplayed()
        composeTestRule.onNodeWithText("Daily Goals").assertIsDisplayed()

        // Find the SearchBar input field by placeholder text "Search"
        val searchInput = composeTestRule.onNode(
            hasSetTextAction() and hasText("Search").or(hasText(""))
        )

        // Perform typing into the search input
        searchInput.performTextInput("Hello")

        // Verify the query change callback updated the capturedQuery
        assert(capturedQuery == "Hello")
    }
}
