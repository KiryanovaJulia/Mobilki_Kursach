package com.example.playlistmaker.ui.search.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.ebm.R
import com.example.ebm.databinding.FavoriteSongListItemBinding
import com.example.ebm.domain.search.models.Track


class TrackViewHolder(private val binding: FavoriteSongListItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(model: Track){
        binding.trackTitle.text = model.trackName
        Glide.with(itemView)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .fitCenter()
            .transform(RoundedCorners(itemView.context.resources.getDimensionPixelSize(R.dimen.track_image_rounding)))
            .into(binding.trackImageIv)
        binding.trackAuthor.text = model.artistName.trim()
        binding.durationTv.text = model.trackTimeMillis
    }
}