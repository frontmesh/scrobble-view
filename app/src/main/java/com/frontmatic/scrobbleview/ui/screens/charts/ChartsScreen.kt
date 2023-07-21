package com.frontmatic.scrobbleview.ui.screens.charts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import com.frontmatic.scrobbleview.ui.components.Header
import com.frontmatic.scrobbleview.ui.components.TabRow
import com.frontmatic.scrobbleview.ui.components.TabTitle
import com.frontmatic.scrobbleview.ui.screens.charts.tabs.RecentTab
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

val tabs = listOf(
    "Recent", "Seven Days", "One Month", "Three Months", "Six Months", "One Year", "Overall"
)


@OptIn(ExperimentalFoundationApi::class)
@Destination(
    route = "charts"
)
@Composable
fun ChartsScreen(
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var scrollToPositions = remember { mutableStateListOf<Float>() }

    LaunchedEffect(key1 = pagerState.currentPage) {
        scrollState.animateScrollTo(scrollToPositions[pagerState.currentPage].roundToInt() - 16)
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
                selectedTabPosition = pagerState.currentPage
            ) {
                tabs.forEachIndexed { index, text ->
                    TabTitle(
                        title = text,
                        position = index,
                        color = if (pagerState.currentPage == index)
                            MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.onGloballyPositioned { coordinates ->
                            scrollToPositions.add(coordinates.positionInRoot().x)
                        }
                    ) {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                }
            }
        }

        TabContent(pagerState = pagerState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabContent(pagerState: PagerState) {
    HorizontalPager(pageCount = tabs.size, state = pagerState) { page ->
        Column {
//            Header(text = tabs[pagerState.currentPage])

            when (pagerState.currentPage) {
                0 -> RecentTab()
    //            1 -> SevenDaysTab()
    //            2 -> OneMonthTab()
    //            3 -> ThreeMonthsTab()
    //            4 -> SixMonthsTab()
    //            5 -> OneYearTab()
    //            6 -> OverallTab()
            }
        }
    }
}