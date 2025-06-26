package com.zachnr.bookplayfree.dashboard.presentation.pages.library

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.zachnr.bookplayfree.dashboard.presentation.pages.library.state.LibraryEvent
import com.zachnr.bookplayfree.dashboard.presentation.pages.library.state.LibraryState
import com.zachnr.bookplayfree.domain.model.DomainWrapper
import com.zachnr.bookplayfree.domain.repository.BookRepository
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.navigation.route.DashboardDestinations
import com.zachnr.bookplayfree.uicomponent.base.BaseViewModel
import com.zachnr.bookplayfree.uicomponent.base.ViewEffect
import com.zachnr.bookplayfree.utils.utils.DispatcherProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LibraryViewModel(
    navigator: Navigator,
    private val dispatcher: DispatcherProvider,
    private val bookRepository: BookRepository
) : BaseViewModel<LibraryState, LibraryEvent, ViewEffect>(navigator) {

    init {
        loadBooks()
    }

    override fun setInitialState(): LibraryState = LibraryState(isLoading = true)
    public override fun handleEvents(event: LibraryEvent) {
        when (event) {
            is LibraryEvent.ClickReadBook -> navigateToDetail(event.uri)
            is LibraryEvent.LoadPdf -> loadPdf(event.uri)
            is LibraryEvent.SearchExpandedChanged -> updateState {
                it.copy(isSearchActive = event.isExpanded)
            }

            is LibraryEvent.SearchQueryTextChanged -> updateState {
                it.copy(searchQueryText = event.query)
            }
        }
    }

    private fun navigateToDetail(bookUri: Uri) {
        navigate(DashboardDestinations.ReadingBookScreen(uri = bookUri.toString()))
    }

    private fun loadPdf(uri: Uri?) = viewModelScope.launch(dispatcher.io) {
        updateState { it.copy(isLoading = true, errorMessage = null) }
        when (val books = bookRepository.getAllPdfBooks(uri)) {
            is DomainWrapper.Error -> updateState {
                it.copy(isLoading = false, errorMessage = books.message)
            }

            is DomainWrapper.Success -> updateState {
                it.copy(isLoading = false, books = books.data)
            }
        }
    }

    private fun loadBooks() = viewModelScope.launch(dispatcher.io) {
        updateState { it.copy(isLoading = true, errorMessage = null) }
        delay(DUMMY_DELAY)
        updateState { it.copy(isLoading = false, books = emptyList()) }
    }

    companion object {
        private const val DUMMY_DELAY = 1000L // Simulate network delay
    }
}
