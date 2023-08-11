package com.frontmatic.scrobbleview.ui.screens.track

import androidx.lifecycle.ViewModel
import com.frontmatic.scrobbleview.data.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrackDetailViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {

}