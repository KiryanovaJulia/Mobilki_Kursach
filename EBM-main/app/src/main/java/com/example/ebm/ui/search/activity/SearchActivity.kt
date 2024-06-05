package com.example.ebm.ui.search.activity

import TrackListAdapter
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ebm.App
import com.example.ebm.R
import com.example.ebm.databinding.ActivitySearchBinding
import com.example.ebm.domain.search.models.Playlist
import com.example.ebm.domain.search.models.Track
import com.example.ebm.ui.search.SearchState
import com.example.ebm.ui.search.adapters.PlaylistListAdapter
import com.example.ebm.ui.search.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchBinding
    private val viewModel by viewModel<SearchViewModel>()
    private var searchFieldEmpty: Boolean = true
    private var tracks = ArrayList<Track>()
    private var playlists = ArrayList<Playlist>()
    private lateinit var playlistListAdapter: PlaylistListAdapter
    private lateinit var trackListAdapter: TrackListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getScreenStateLiveData().observe(this){
            renderState(it)
        }
        val sharedPrefs = getSharedPreferences(EBM_PREFERENCES, MODE_PRIVATE)
        playlistListAdapter = PlaylistListAdapter(playlists)
        trackListAdapter = TrackListAdapter(tracks, viewModel)
        binding.favoritesRv.adapter = trackListAdapter
        binding.favoritesRv.layoutManager = LinearLayoutManager(this)
        binding.songswitchRecyclerview.adapter = playlistListAdapter
        binding.songswitchRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.favoritesRv.isVisible = true
        binding.songswitchRecyclerview.isVisible = true
        if((applicationContext as App).darkTheme){
           binding.darkModeSwitch.isChecked = true
        }
        binding.darkModeSwitch.setOnCheckedChangeListener { switcher, isChecked ->
            (applicationContext as App).switchTheme(isChecked)
            if (isChecked){
                sharedPrefs
                    .edit()
                    .putString(THEME_MODE_KEY, DARK_MODE_VALUE)
                    .apply()
            }
            else{
                sharedPrefs
                    .edit()
                    .putString(THEME_MODE_KEY, LIGHT_MODE_VALUE)
                    .apply()
            }
        }

        val searchFieldTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()){
                    if(binding.searchField.hasFocus()){
                    }
                    else{
                        setDefaultScreenState()
                    }
                    binding.clearButton.isVisible = false
                }
                else{
                    setDefaultScreenState()
                    searchFieldEmpty = false
                    binding.clearButton.isVisible = true
                    viewModel.setSearchData(s.toString())
                    viewModel.searchDebounce()
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        }
        binding.searchField.addTextChangedListener(searchFieldTextWatcher)
        binding.searchField.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus && binding.searchField.text?.isEmpty() != false){

            }
        }
        binding.backBtn.setOnClickListener {
            viewModel.showPlaylists()
            viewModel.showHistory()
            setDefaultScreenState()
        }
        binding.clearButton.setOnClickListener {
            tracks.clear()
            trackListAdapter.notifyDataSetChanged()
            currentFocus?.let {
                val inputMethodManager = ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
                inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            }
            binding.searchField.setText("")
        }

        binding.searchField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.immediateSearch()
            }
            false
        }
    }

    private fun setNetworkErrorScreenState(){
        setDefaultScreenState()
        Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show()
    }
    private fun setPlaylistsShowingScreenState(playlistsInput: List<Playlist>){
        setDefaultScreenState()
        binding.songswitchRecyclerview.isVisible = true
        playlists.clear()
        playlists.addAll(playlistsInput)
        playlistListAdapter.notifyDataSetChanged()
    }
    private fun setLoadingScreenState(){
        setDefaultScreenState()
        binding.favoritesRv.isVisible = false
        binding.backBtn.isVisible = false
        binding.welcomeTv.isVisible = false
        binding.welcomeTextTv.isVisible = false
        binding.tabLayout.isVisible = false
        binding.songswitchRecyclerview.isVisible = false
        binding.favoritesTv.text = getString(R.string.search_results)
        binding.searchPb.isVisible = true
    }
    private fun setEmptyResultsScreenState(){
        setDefaultScreenState()
        Toast.makeText(this, getString(R.string.nothing_found), Toast.LENGTH_SHORT).show()
    }
    private fun setDefaultScreenState(){
        binding.favoritesRv.isVisible = true
        binding.searchPb.isVisible = false
        binding.backBtn.isVisible = false
        binding.welcomeTv.isVisible = true
        binding.welcomeTextTv.isVisible = true
        binding.tabLayout.isVisible = true
        binding.songswitchRecyclerview.isVisible = true
        binding.favoritesTv.text = getString(R.string.search_history)
    }
    private fun setSearchHistoryScreenState(searchHistory: List<Track>){
        setDefaultScreenState()
        binding.favoritesRv.isVisible = true
        tracks.clear()
        tracks.addAll(searchHistory)
        trackListAdapter.notifyDataSetChanged()
    }
    private fun setContentScreenState(results: List<Track>){
        binding.favoritesRv.isVisible = true
        binding.searchPb.isVisible = false
        binding.backBtn.isVisible = true
        binding.welcomeTv.isVisible = false
        binding.welcomeTextTv.isVisible = false
        binding.tabLayout.isVisible = false
        binding.songswitchRecyclerview.isVisible = false
        binding.favoritesTv.text = getString(R.string.search_results)
        tracks.clear()
        tracks.addAll(results)
        trackListAdapter.notifyDataSetChanged()
    }
    private fun renderState(state: SearchState){
        when(state){
            is SearchState.Default ->{setDefaultScreenState()}
            is SearchState.Loading ->{setLoadingScreenState()}
            is SearchState.NetworkError ->{setNetworkErrorScreenState()}
            is SearchState.EmptyResults ->{setEmptyResultsScreenState()}
            is SearchState.SearchHistory ->{setSearchHistoryScreenState(state.tracks)}
            is SearchState.Content ->{setContentScreenState(state.tracks)}
            is SearchState.Playlists->{setPlaylistsShowingScreenState(state.playlists)}
        }

    }

    override fun onResume() {
        super.onResume()
        //хихи
        viewModel.fetchDataForDefault()
    }
    companion object{
        const val EBM_PREFERENCES = "EBM_PREFERENCES"
        const val DARK_MODE_VALUE = "dark"
        const val LIGHT_MODE_VALUE = "light"
        const val THEME_MODE_KEY = "key_for_theme_mode"
    }
}