package com.example.ebm.ui.search.viewmodel

import SearchHistoryInteractor
import TracksInteractor
import TracksSearchResult
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ebm.domain.search.PlaylistStorageInteractor
import com.example.ebm.domain.search.models.Track
import com.example.ebm.ui.search.SearchState

class SearchViewModel(
    private val tracksInteractor: TracksInteractor,
    private val searchHistoryInteractor: SearchHistoryInteractor,
    private val playlistStorageInteractor: PlaylistStorageInteractor) : ViewModel(){
    private var screenStateLiveData = MutableLiveData<SearchState>(SearchState.Default)
    private var searchData: String = SEARCH_DEF
    private var lastSearch: String = SEARCH_DEF
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable {
        iTunesSearch(searchData)
    }
    init {
        handler.post { showHistory() }
        handler.postDelayed({showPlaylists()},500L)
    }
    fun fetchDataForDefault(){
        handler.post { showHistory() }
        handler.postDelayed({showPlaylists()},500L)
    }
    fun searchDebounce() {
        handler.removeCallbacks(searchRunnable, SEARCH_RUNNABLE_TOKEN)
        handler.postDelayed(searchRunnable ,
            SEARCH_RUNNABLE_TOKEN,
            SEARCH_DEBOUNCE_DELAY
        )
    }
    fun setSearchData(input: String){
        searchData = input
    }
    fun getScreenStateLiveData(): LiveData<SearchState> = screenStateLiveData

    fun clearHistory(){
        searchHistoryInteractor.clear()
        renderState(SearchState.Default)
    }
    fun showHistory(){
        val history = searchHistoryInteractor.read()
        if(history.isNotEmpty()){
            renderState(SearchState.SearchHistory(history))
        }else{
            renderState(SearchState.Default)
        }
    }
    fun showPlaylists(){
        val playlists = playlistStorageInteractor.getAll()
        if(playlists.isNotEmpty()){
            renderState(SearchState.Playlists(playlists))
        }else{
            renderState(SearchState.Default)
        }
    }
    fun writeToHistory(input: Track){
        searchHistoryInteractor.write(input)
    }
    fun immediateSearch(){
        handler.removeCallbacks(searchRunnable, SEARCH_RUNNABLE_TOKEN)
        iTunesSearch(searchData)
    }
    fun searchLast(){
        handler.removeCallbacks(searchRunnable, SEARCH_RUNNABLE_TOKEN)
        iTunesSearch(lastSearch)
    }
    private fun iTunesSearch(query: String) {
        tracksInteractor.searchTracks(query, object: TracksInteractor.TracksConsumer {
            override fun consume(searchResult: TracksSearchResult) {
                handler.post {
                    when(searchResult.type){
                        SearchResultType.SUCCESS ->{
                            renderState(SearchState.Content(searchResult.tracks))
                        }
                        SearchResultType.EMPTY -> {
                            renderState(SearchState.EmptyResults)
                        }
                        SearchResultType.LOADING -> {
                            renderState(SearchState.Loading)
                        }
                        SearchResultType.ERROR -> {
                            renderState(SearchState.NetworkError)
                            lastSearch = query
                        }
                    }
                }

            }
        }
        )
    }

    private fun renderState(state: SearchState){
        screenStateLiveData.postValue(state)
    }
    fun onDestroy(){
        handler.removeCallbacksAndMessages(null)
    }
    companion object {
        const val SEARCH_RUNNABLE_TOKEN = 1
        const val SEARCH_DEF = ""
        const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}