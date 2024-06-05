package com.example.ebm.domain.search.impl

import com.example.ebm.domain.search.models.Track
import com.example.ebm.domain.search.PlaylistStorageInteractor
import com.example.ebm.domain.search.PlaylistStorageRepository
import com.example.ebm.domain.search.models.Playlist

class PlaylistStorageInteractorImpl(private val playlistStorageRepository: PlaylistStorageRepository):
    PlaylistStorageInteractor {
    override fun write(input: Playlist) {
        playlistStorageRepository.write(input)
    }

    override fun clear() {
        playlistStorageRepository.clear()
    }

    override fun getAll(): List<Playlist> {
        return playlistStorageRepository.getAll()
    }

    override fun getById(id: Int): Playlist? {
         return getById(id)
    }

    override fun addToFavorites(track: Track) {
        playlistStorageRepository.addToFavorites(track)
    }

}