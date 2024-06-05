package com.example.ebm.ui

import TrackListAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.ebm.R
import com.example.ebm.databinding.ActivityPlayerBinding
import com.example.ebm.databinding.ActivityPlaylistBinding
import com.example.ebm.domain.search.models.Playlist
import com.example.ebm.ui.player.activity.PlayerActivity
import com.example.ebm.ui.search.viewmodel.SearchViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaylistBinding
    private lateinit var adapter: TrackListAdapter
    private val viewModel by viewModel<SearchViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val playlist = Gson().fromJson(intent.getStringExtra(PLAYLIST_KEY), Playlist::class.java)
        if(playlist.tracks.isNotEmpty()){
            Glide.with(this)
                .load(playlist.tracks[0].artworkUrl100.replaceAfterLast('/',"512x512bb.jpg"))
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .transform(RoundedCorners(this.resources.getDimensionPixelSize(R.dimen.track_image_rounding)))
                .into(binding.playlistImageIv)
        }
        else{
            binding.playlistImageIv.background = getDrawable(R.drawable.placeholder)
        }
        binding.trackTitle.text = playlist.name
        binding.trackSubText.text = playlist.subtitle
        adapter = TrackListAdapter(playlist.tracks, viewModel)
        binding.favoritesRv.adapter = adapter
        binding.backBtn.setOnClickListener {
            finish()
        }

    }
    companion object{
        const val PLAYLIST_KEY = "PLAYLIST_KEY"
    }
}