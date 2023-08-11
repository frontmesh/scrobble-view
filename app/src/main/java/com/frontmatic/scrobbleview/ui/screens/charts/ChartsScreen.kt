package com.frontmatic.scrobbleview.ui.screens.charts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
//import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.scrobbleview.data.api.RequestPeriod
import com.frontmatic.scrobbleview.ui.components.TabRow
import com.frontmatic.scrobbleview.ui.components.TabTitle
import com.frontmatic.scrobbleview.ui.screens.charts.tabs.RecentTab
import com.frontmatic.scrobbleview.ui.screens.charts.tabs.TopTrackTab
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

val tabs = listOf(
    "Recent",
    "Seven Days",
    "One Month",
    "Three Months",
    "Six Months",
    "One Year",
    "Overall"
)

@OptIn(ExperimentalFoundationApi::class)
@Destination(
    route = "charts"
)
@Composable
fun ChartsScreen(
    navigator: DestinationsNavigator,
    chartViewModel: ChartViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    )
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val currentPage = pagerState.currentPage

    LaunchedEffect(key1 = currentPage) {

        if (currentPage < 3) {
            scrollState.scrollTo(0)
        } else {
            scrollState.scrollTo(1000)
        }
    }

    Column(
        verticalArrangement = Arrangement.Top,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .horizontalScroll(scrollState)
        ) {
            TabRow(
                selectedTabPosition = currentPage
            ) {
                tabs.forEachIndexed { index, tab ->
                    TabTitle(
                        title = tab,
                        position = index,
                        color = if (currentPage == index)
                            MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary,
                    ) {
                        scope.launch {
                            pagerState.scrollToPage(index)
                        }
                    }
                }
            }
        }
        TabContent(
            pagerState = pagerState,
            navigator = navigator,
            viewModel = chartViewModel,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabContent(
    pagerState: PagerState,
    navigator: DestinationsNavigator,
    viewModel: ChartViewModel
) {
    HorizontalPager(
        modifier = Modifier,
        pageCount = tabs.size,
        state = pagerState,
    ) {
            Column {
                when (it) {
                    0 -> RecentTab(navigator = navigator)
                    else -> {
                        val (tracks, isRefreshing) = when (it) {
                            1 -> Pair(
                                viewModel.sevenDaysTopTracks,
                                viewModel.isSevenDayTabRefreshing.value
                            )

                            2 -> Pair(
                                viewModel.oneMonthTopTracks,
                                viewModel.isOneMonthTabRefreshing.value
                            )

                            3 -> Pair(
                                viewModel.threeMonthsTopTracks,
                                viewModel.isThreeMonthTabRefreshing.value
                            )

                            4 -> Pair(
                                viewModel.sixMonthsTopTracks,
                                viewModel.isSixMonthTabRefreshing.value
                            )

                            5 -> Pair(
                                viewModel.twelveMonthsTopTracks,
                                viewModel.isTwelveMonthTabRefreshing.value
                            )

                            6 -> Pair(
                                viewModel.overallTopTracks,
                                viewModel.isOverallTabRefreshing.value
                            )

                            else -> Pair(
                                viewModel.sevenDaysTopTracks,
                                viewModel.isSevenDayTabRefreshing.value
                            )
                        }
                        TopTrackTab(
                            tracks = tracks,
                            navigator = navigator,
                            isRefreshing = isRefreshing
                        )
                    }
                }
            }
        }
}