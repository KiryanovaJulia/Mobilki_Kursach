package com.example.ebm.domain.search.models

data class Playlist(
    val id: Int,
    val name: String,
    val subtitle: String,
    val imageUrl: String,
    val tracks: ArrayList<Track>
    )