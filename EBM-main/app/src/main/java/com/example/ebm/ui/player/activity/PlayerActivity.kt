package com.example.ebm.ui.player.activity

import PlayerViewModel
import com.example.ebm.domain.search.models.Track
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.ebm.R
import com.example.ebm.databinding.ActivityPlayerBinding
import com.example.ebm.ui.LyricsActivity
import com.example.ebm.ui.PlaylistActivity
import com.example.ebm.ui.player.PlayerState
import com.example.ebm.ui.search.adapters.PlaylistListAdapter
import com.google.gson.Gson

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding
    private val viewModel: PlayerViewModel by viewModel<PlayerViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val track = Gson().fromJson(intent.getStringExtra(TRACK_PLAYER_KEY), Track::class.java)
        viewModel.preparePlayer(track)
        viewModel.getScreenStateLiveData().observe(this){
            renderState(it)
        }
        viewModel.getCurrentPositionLiveData().observe(this){
            binding.currentPositionTv.text = it.first
            binding.seekbar.progress = it.second
        }
        binding.backBtn.setOnClickListener { finish() }
        binding.playBtn.setOnClickListener {
            viewModel.playbackControl()
        }
        binding.favoritesBtn.setOnClickListener {
            binding.favoritesBtn.setImageResource(R.drawable.favorite_pressed)
            viewModel.addToFavorites(track)
        }
        Glide.with(this)
            .load(track.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg"))
            .placeholder(R.drawable.placeholder)
            .fitCenter()
            .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.track_image_rounding)))
            .into(binding.trackImageIv)
        binding.learnTheTextTv.setOnClickListener {
            val navigateToLyrics = Intent(this, LyricsActivity::class.java)
            navigateToLyrics.putExtra(LYRICS_KEY, Gson().toJson(track))
            startActivity(navigateToLyrics)
        }
        binding.trackTitle.text = track.trackName
        binding.trackSubText.text = track.artistName
        binding.durationTv.text = getString(R.string.thirty_seconds)
        binding.seekbar.max = 30000

    }
    private fun renderState(state: PlayerState){
        when(state){
            is PlayerState.Default -> Unit
            is PlayerState.Prepared ->{
                binding.playBtn.setImageResource(R.drawable.play_btn)
                binding.currentPositionTv.text = getString(R.string.zeropos)
                binding.seekbar.progress = 0
            }
            is PlayerState.Playing ->{
                binding.playBtn.setImageResource(R.drawable.pause_button)
            }
            is PlayerState.Paused ->{
                binding.playBtn.setImageResource(R.drawable.play_btn)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.pausePlayer()
    }
    override fun onDestroy() {
        super.onDestroy()
        viewModel.releasePlayer()
    }
    companion object{
        const val TRACK_PLAYER_KEY = "TRACK_PLAYER_KEY"
        const val LYRICS_KEY = "LYRICS_KEY"
    }
}