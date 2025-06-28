package com.zachnr.bookplayfree.uicomponent.searchbar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zachnr.bookplayfree.uicomponent.R

/**
 * A composable Search Bar component tailored for dashboard page.
 *
 * This component displays a search bar with a customizable placeholder,
 * handles search query input, and shows search results content when expanded.
 *
 * @param placeholder The placeholder text shown in the search input field.
 *                    Defaults to the localized string resource for "search".
 * @param onSearch Callback invoked when the user submits a search.
 *                 Receives the current search query as a parameter.
 *                 Defaults to an empty lambda.
 * @param searchResultContent A composable lambda that defines the UI content
 *                            to display when search results are shown.
 *                            Defaults to an empty composable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarDashboard(
    placeholder: String = stringResource(R.string.search),
    onSearch: (String) -> Unit = {},
    searchResultContent: @Composable () -> Unit = {},
) {
    var searchBarState by remember { mutableStateOf(SearchBarState()) }
    val searchBarColor = SearchBarDefaults.colors()
    val searchBarPadding = animateDpAsState(
        targetValue = if (searchBarState.isActive) 0.dp else 16.dp,
        label = "Search bar padding"
    )
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = searchBarState.searchQuery,
                onQueryChange = { searchBarState = searchBarState.copy(searchQuery = it) },
                onSearch = { onSearch(searchBarState.searchQuery) },
                expanded = searchBarState.isActive,
                onExpandedChange = { searchBarState = searchBarState.copy(isActive = it) },
                enabled = true,
                placeholder = { Text(placeholder) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = placeholder) },
                trailingIcon = null,
                interactionSource = null
            )
        },
        expanded = searchBarState.isActive,
        onExpandedChange = { searchBarState = searchBarState.copy(isActive = it) },
        shape = SearchBarDefaults.inputFieldShape,
        colors = searchBarColor,
        tonalElevation = SearchBarDefaults.TonalElevation,
        shadowElevation = SearchBarDefaults.ShadowElevation,
        windowInsets = SearchBarDefaults.windowInsets,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = searchBarPadding.value)

    ) {
        searchResultContent()
    }
}
