package com.example.ebm.ui.search.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.ebm.R
import com.example.ebm.databinding.SongRecyclerviewItemBinding
import com.example.ebm.domain.search.models.Playlist

class PlaylistViewHolder(private var binding : SongRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(model: Playlist){
        if(model.tracks.isNotEmpty()){
            Glide.with(itemView)
                .load(model.tracks[0].artworkUrl100)
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .transform(RoundedCorners(itemView.context.resources.getDimensionPixelSize(R.dimen.track_image_rounding)))
                .into(binding.playlistImageIv)
            binding.titleTv.text = model.name
            binding.subtitleTv.text = model.subtitle
        }
        else{
            binding.playlistImageIv.background = itemView.context.getDrawable(R.drawable.placeholder)
            binding.titleTv.text = model.name
            binding.subtitleTv.text = model.subtitle
        }


    }
}