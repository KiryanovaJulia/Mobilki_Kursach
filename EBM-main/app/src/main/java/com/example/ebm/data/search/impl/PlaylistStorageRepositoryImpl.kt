package com.example.ebm.data.search.impl

import com.example.ebm.domain.search.models.Track
import com.example.ebm.data.search.LocalPlaylistStorageHandler
import com.example.ebm.domain.search.PlaylistStorageRepository
import com.example.ebm.domain.search.models.Playlist


class PlaylistStorageRepositoryImpl(private val localPlaylistStorageHandler: LocalPlaylistStorageHandler):
    PlaylistStorageRepository {
    override fun write(input: Playlist) {
        localPlaylistStorageHandler.write(input)
    }

    override fun clear() {
        localPlaylistStorageHandler.clear()
    }

    override fun getAll(): List<Playlist> {
        return localPlaylistStorageHandler.getAll()
    }

    override fun getById(id: Int): Playlist? {
        return localPlaylistStorageHandler.getById(id)
    }

    override fun addToFavorites(track: Track) {
        localPlaylistStorageHandler.addToFavorites(track)
    }
}