package com.example.clone_tiktok.ui_app.ui.for_you

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.Log
import com.example.clone_tiktok.ui_app.ui.VideoDetailScreen
import com.example.clone_tiktok.ui_app.video.VideoViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListVideoForYouScreen(
    modifier: androidx.compose.ui.Modifier.Companion,
    onShowComment : (Int) -> Unit
) {
val pageCount = rememberPagerState(pageCount = {10});



    VerticalPager(state = pageCount) {
videoId ->
        val viewModel : VideoViewModel = hiltViewModel(key = videoId.toString());

    VideoDetailScreen(videoId = videoId, viewModel,onShowComment = onShowComment );
    }
}