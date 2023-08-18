package com.frontmatic.scrobbleview.ui.screens.track

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.frontmatic.scrobbleview.data.UseCases
import com.frontmatic.scrobbleview.data.repository.TrackRepository
import com.frontmatic.scrobbleview.ui.screens.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrackDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val trackRepository: TrackRepository,
) : ViewModel() {
    val trackInfo  by lazy {
        val args = savedStateHandle.navArgs<TrackScreenNavArgs>()
        val track = args.track
        val artist = args.artist
        Log.d("TrackDetailViewModel", "track: $track, artist: $artist")
        trackRepository.getTrackInfoByArtistAndTrack(artist, track)
    }

//    init {
//        val args = savedStateHandle.navArgs<TrackScreenNavArgs>()
//        val track = args.track
//        val artist = args.artist
//        Log.d("TrackDetailViewModel", "track: $track, artist: $artist")
//////        trackRepository.trackInfoStore.stream()
////    }
//
//
//    fun getTrackInfoByArtistAndTrack(artis, tracl) =
//        trackRepository.getTrackInfoByArtistAndTrack(artist, track)

}