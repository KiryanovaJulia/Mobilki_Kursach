package com.example.ebm.domain.search

import com.example.ebm.domain.search.models.Track
import com.example.ebm.domain.search.models.Playlist

interface PlaylistStorageRepository {
    fun write(input: Playlist)
    fun clear()
    fun getAll(): List<Playlist>
    fun getById(id: Int): Playlist?
    fun addToFavorites(track: Track)
}