package com.zachnr.bookplayfree.dashboard.presentation.pages.library

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zachnr.bookplayfree.dashboard.presentation.pages.library.state.LibraryEvent
import com.zachnr.bookplayfree.dashboard.presentation.pages.library.state.LibraryState
import com.zachnr.bookplayfree.designsystem.icons.BpfIcons
import com.zachnr.bookplayfree.designsystem.theme.GreenForest
import com.zachnr.bookplayfree.domain.model.BookDomain
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun LibraryScreen(
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle()
    val folderPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) { uri ->
        if (uri != null) {
            context.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            viewModel.sendEvent(LibraryEvent.LoadPdf(uri))
        }
    }

    LibraryScreen(
        modifier = modifier,
        state = state.value,
        event = viewModel::sendEvent,
        pickerLauncher = { folderPickerLauncher.launch(null) }
    )
}

@Composable
private fun LibraryScreen(
    modifier: Modifier = Modifier,
    state: LibraryState,
    event: (LibraryEvent) -> Unit = {},
    pickerLauncher: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchBarComponent(state, event)
        when {
            state.isLoading -> Loading(modifier)
            state.errorMessage != null -> Error(modifier, state.errorMessage)
            state.books.isEmpty() -> Empty(modifier, pickerLauncher)
            else -> Content(modifier, state.books)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBarComponent(state: LibraryState, event: (LibraryEvent) -> Unit) {
    val colors1 = SearchBarDefaults.colors()
    val searchBarPadding = animateDpAsState(
        targetValue = if (state.isSearchActive) 0.dp else 16.dp,
        label = "Search bar padding"
    )
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = state.searchQueryText,
                onQueryChange = { event(LibraryEvent.SearchQueryTextChanged(it)) },
                onSearch = { /* Handle search */ },
                expanded = state.isSearchActive,
                onExpandedChange = { event(LibraryEvent.SearchExpandedChanged(it)) },
                enabled = true,
                placeholder = { Text("Search") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = null,
                interactionSource = null
            )
        },
        expanded = state.isSearchActive,
        onExpandedChange = { event(LibraryEvent.SearchExpandedChanged(it)) },
        shape = SearchBarDefaults.inputFieldShape,
        colors = colors1,
        tonalElevation = SearchBarDefaults.TonalElevation,
        shadowElevation = SearchBarDefaults.ShadowElevation,
        windowInsets = SearchBarDefaults.windowInsets,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = searchBarPadding.value)

    ) {
        // TODO: Add search result
    }
    Spacer(modifier = Modifier.height(18.dp))
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Loading...", modifier = Modifier.align(Alignment.Center))
    }
}


@Composable
private fun Error(modifier: Modifier = Modifier, message: String) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Error: $message",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun Empty(modifier: Modifier = Modifier, pickerLauncher: () -> Unit) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No books found in library.")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { pickerLauncher() }) {
                Text(text = "Scan Book Folder")
            }
        }

    }
}

@Composable
private fun Content(modifier: Modifier = Modifier, books: List<BookDomain>) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(books) { book ->
            BookItemView(book = book, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun BookItemView(
    book: BookDomain,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = book.thumbnail?.asImageBitmap()?.let { BitmapPainter(it) }
                ?: painterResource(BpfIcons.warning),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp, 108.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.height(108.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontStyle = FontStyle.Italic,
                        color = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
            Column {
                Text(
                    text = "${(book.progress * 100).toInt()}% Progress reading",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = book.progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = GreenForest,
                    trackColor = Color.LightGray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LibraryScreenPreview() {
    LibraryScreen(
        state = LibraryState(
            books = listOf(
                BookDomain(
                    id = "1",
                    title = "Sample Book",
                    author = "John Doe",
                    thumbnail = null,
                    uri = Uri.EMPTY
                ),
                BookDomain(
                    id = "1",
                    title = "Sample Book",
                    author = "John Doe",
                    thumbnail = null,
                    uri = Uri.EMPTY
                ),
                BookDomain(
                    id = "1",
                    title = "Sample Book",
                    author = "John Doe",
                    thumbnail = null,
                    uri = Uri.EMPTY
                )
            )
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun BookItemViewPreview() {
    BookItemView(
        book = BookDomain(
            id = "1",
            title = "Sample Book",
            author = "John Doe",
            thumbnail = null,
            uri = Uri.EMPTY
        )
    )
}