package com.example.ebm.data.search.impl

import com.example.ebm.domain.search.models.Track
import android.content.SharedPreferences
import com.example.ebm.data.search.LocalPlaylistStorageHandler
import com.example.ebm.domain.search.models.Playlist
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesLocalPlaylistStorageHandler(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) :
    LocalPlaylistStorageHandler {
    override fun write(input: Playlist) {
        val currentStoredPlaylists = getAll().toMutableList()
        currentStoredPlaylists.add(0, input)
        clear()
        sharedPreferences
            .edit()
            .putString(PLAYLIST_STORAGE_KEY, gson.toJson(currentStoredPlaylists))
            .apply()
    }

    override fun clear() {
        sharedPreferences
            .edit()
            .remove(PLAYLIST_STORAGE_KEY)
            .apply()
    }

    override fun getAll(): List<Playlist> {
        val json = sharedPreferences.getString(PLAYLIST_STORAGE_KEY, null) ?: return emptyList()
        return gson.fromJson(json, object : TypeToken<List<Playlist>>() {}.type)
    }

    override fun getById(id: Int): Playlist? {
        val all = getAll()
        return all.find { it.id == id }
    }

    override fun addToFavorites(track: Track) {
        val favoritesPlaylist = getById(0) ?: Playlist(0, "Favorites", "learn again","", arrayListOf())
        favoritesPlaylist.tracks.add(track)
        val currentStoredPlaylists = getAll().toMutableList()
        if(currentStoredPlaylists.size!=0){
            currentStoredPlaylists[0] = favoritesPlaylist
        }
        else{
            currentStoredPlaylists.add(favoritesPlaylist)
        }
        clear()
        sharedPreferences
            .edit()
            .putString(PLAYLIST_STORAGE_KEY, gson.toJson(currentStoredPlaylists))
            .apply()


    }

    companion object {
        const val PLAYLIST_STORAGE_KEY = "playlist_storage_key"
    }
}