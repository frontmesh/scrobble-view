package com.frontmatic.scrobbleview.ui.screens.charts

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.frontmatic.scrobbleview.data.UseCases
import com.frontmatic.scrobbleview.data.api.RequestPeriod
import com.frontmatic.scrobbleview.data.model.TopTrack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val useCases: UseCases,
): ViewModel() {

    val sevenDaysTopTracks: Flow<PagingData<TopTrack>> = useCases.getAllTopTracks(RequestPeriod.SEVEN_DAY)
    val isSevenDayTabRefreshing = mutableStateOf(false)

    val oneMonthTopTracks: Flow<PagingData<TopTrack>> = useCases.getAllTopTracks(RequestPeriod.ONE_MONTH)
    val isOneMonthTabRefreshing = mutableStateOf(false)

    val threeMonthsTopTracks: Flow<PagingData<TopTrack>> = useCases.getAllTopTracks(RequestPeriod.THREE_MONTH)
    val isThreeMonthTabRefreshing = mutableStateOf(false)

    val sixMonthsTopTracks: Flow<PagingData<TopTrack>> = useCases.getAllTopTracks(RequestPeriod.SIX_MONTH)
    val isSixMonthTabRefreshing = mutableStateOf(false)

    val twelveMonthsTopTracks: Flow<PagingData<TopTrack>> = useCases.getAllTopTracks(RequestPeriod.TWELVE_MONTH)
    val isTwelveMonthTabRefreshing = mutableStateOf(false)

    val overallTopTracks: Flow<PagingData<TopTrack>> = useCases.getAllTopTracks(RequestPeriod.OVERALL)
    val isOverallTabRefreshing = mutableStateOf(false)

}