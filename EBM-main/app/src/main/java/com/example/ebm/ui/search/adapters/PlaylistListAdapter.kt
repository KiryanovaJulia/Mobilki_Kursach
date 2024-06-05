package com.example.ebm.ui.search.adapters

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ebm.databinding.FavoriteSongListItemBinding
import com.example.ebm.databinding.SongRecyclerviewItemBinding
import com.example.ebm.domain.search.models.Playlist
import com.example.ebm.ui.PlaylistActivity
import com.example.ebm.ui.player.activity.PlayerActivity
import com.example.ebm.ui.search.viewholders.PlaylistViewHolder
import com.example.ebm.ui.search.viewmodel.SearchViewModel
import com.example.playlistmaker.ui.search.viewholders.TrackViewHolder
import com.google.gson.Gson

class PlaylistListAdapter(private val playlists: List<Playlist>): RecyclerView.Adapter<PlaylistViewHolder>() {
    private var isClickAllowed = true

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return PlaylistViewHolder(SongRecyclerviewItemBinding.inflate(layoutInspector, parent, false))
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlists[position])
        holder.itemView.setOnClickListener{
            if(clickDebounce()){
                val navigateToPlaylistActivity = Intent(holder.itemView.context, PlaylistActivity::class.java)
                navigateToPlaylistActivity.putExtra(PLAYLIST_KEY, Gson().toJson(playlists[position]))
                holder.itemView.context.startActivity(navigateToPlaylistActivity)
            }
        }
    }
    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }
    companion object{
        const val PLAYLIST_KEY = "PLAYLIST_KEY"
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}