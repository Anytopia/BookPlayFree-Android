package com.zachnr.bookplayfree.dashboard.presentation.pages.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zachnr.bookplayfree.shared.viewmodel.MainActivitySharedVM
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    mainShareViewModel: MainActivitySharedVM
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        mainShareViewModel.quote.collect {
            viewModel.updateQuote(it)
        }
    }

    HomeScreen(
        modifier = modifier,
        state = state.value,
        onExpandedChange = viewModel::setOnActiveStateSearchChanged,
        onQueryChange = viewModel::setOnQuerySearchChanged
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    modifier: Modifier,
    state: HomeState,
    onQueryChange: (String) -> Unit = {},
    onExpandedChange: (Boolean) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val colors1 = SearchBarDefaults.colors()
        val searchBarPadding = animateDpAsState(
            targetValue = if (state.isSearchActive) 0.dp else 16.dp,
            label = "Search bar padding"
        )
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = state.searchQueryText,
                    onQueryChange = { onQueryChange(it) },
                    onSearch = { /* Handle search */ },
                    expanded = state.isSearchActive,
                    onExpandedChange = { onExpandedChange(it) },
                    enabled = true,
                    placeholder = { Text("Search") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    trailingIcon = null,
                    interactionSource = null
                )
            },
            expanded = state.isSearchActive,
            onExpandedChange = { onExpandedChange(it) },
            shape = SearchBarDefaults.inputFieldShape,
            colors = colors1,
            tonalElevation = SearchBarDefaults.TonalElevation,
            shadowElevation = SearchBarDefaults.ShadowElevation,
            windowInsets = SearchBarDefaults.windowInsets,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(horizontal = searchBarPadding.value)

        ) {
            // TODO: Add search result
        }
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "Welcome",
            fontSize = 24.sp,
            modifier = Modifier.padding(horizontal = 14.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = state.quote,
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(horizontal = 14.dp)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        modifier = Modifier,
        state = HomeState(quote = "\"A reader lives a thousand lives before he dies. The man who never reads lives only one.\" — George R.R. Martin"),
        onQueryChange = {},
        onExpandedChange = {}
    )
}
