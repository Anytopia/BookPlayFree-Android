package com.zachnr.bookplayfree.dashboard.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.getQuote()
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val colors1 = SearchBarDefaults.colors()
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = state.value.searchQueryText,
                    onQueryChange = { viewModel.setOnQuerySearchChanged(it) },
                    onSearch = { /* Handle search */ },
                    expanded = state.value.isSearchActive,
                    onExpandedChange = { viewModel.setOnActiveStateSearchChanged(it) },
                    enabled = true,
                    placeholder = { Text("Search") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    trailingIcon = null,
                    colors = colors1.inputFieldColors,
                    interactionSource = null,
                )
            },
            expanded = state.value.isSearchActive,
            onExpandedChange = { viewModel.setOnActiveStateSearchChanged(it) },
            shape = SearchBarDefaults.inputFieldShape,
            colors = colors1,
            tonalElevation = SearchBarDefaults.TonalElevation,
            shadowElevation = SearchBarDefaults.ShadowElevation,
            windowInsets = SearchBarDefaults.windowInsets,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            // TODO: Add search result
        }
        Content(state = state.value, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun Content(
    modifier: Modifier,
    state: HomeState
) {
    Column(
        modifier = modifier
    ) {
        Text(text = "Welcome")
        Text(text = state.quote)
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
