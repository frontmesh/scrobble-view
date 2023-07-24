package com.frontmatic.scrobbleview.ui.screens.charts.tabs

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.frontmatic.scrobbleview.data.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SevenDaysTabViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {
    val topTracks = useCases.getAllTopTracks()
    val isRefreshing = mutableStateOf(false)
}