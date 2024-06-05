package com.example.ebm.data.search

import com.example.ebm.domain.search.models.Track
import com.example.ebm.domain.search.models.Playlist


interface LocalPlaylistStorageHandler {
    fun write(input: Playlist)
    fun clear()
    fun getAll(): List<Playlist>
    fun getById(id: Int): Playlist?
    fun addToFavorites(track: Track)
}