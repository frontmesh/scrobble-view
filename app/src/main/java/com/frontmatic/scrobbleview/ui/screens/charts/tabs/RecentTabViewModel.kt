package com.frontmatic.scrobbleview.ui.screens.charts.tabs

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.frontmatic.scrobbleview.data.UseCases
import com.frontmatic.scrobbleview.data.model.RecentTrack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RecentTabViewModel @Inject constructor(
    private val useCases: UseCases,
): ViewModel() {
    val recentTracks: Flow<PagingData<RecentTrack>> = useCases.getAllRecentTracks()
}