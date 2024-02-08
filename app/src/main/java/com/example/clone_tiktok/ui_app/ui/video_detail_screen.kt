package com.example.clone_tiktok.ui_app.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import com.example.clone_tiktok.design_system.TiktokPlayer
import com.example.clone_tiktok.ui.theme.Clone_tiktokTheme
import com.example.clone_tiktok.ui_app.video.VideoDetailUiEvent
import com.example.clone_tiktok.ui_app.video.VideoDetailUiState
import com.example.clone_tiktok.ui_app.video.VideoViewModel
import com.example.clone_tiktok.ui_app.video.composable.SideBar
import com.example.clone_tiktok.ui_app.video.composable.VideoInfoArea


@Composable
fun VideoDetailScreen(videoId: Int, videoViewModel : VideoViewModel,  onShowComment:(Int)->Unit) {
    val uiState = videoViewModel.uiState.collectAsState(); // get UI state tu view model
    if(uiState.value is VideoDetailUiEvent.Default){
        videoViewModel.handleAction(VideoDetailUiState.LoadData)
    }

 VideoDetailScreen(uiEvent = uiState.value, player = videoViewModel.videoPlayer, onShowComment = {onShowComment(videoId)}){
     videoViewModel.handleAction(state = it);
 };


}
@Composable
fun VideoDetailScreen(
    uiEvent: VideoDetailUiEvent,
    player: Player,
    onShowComment:()->Unit,
    handleAction:(VideoDetailUiState) -> Unit

){
 when(uiEvent){
     is VideoDetailUiEvent.Loading ->{
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text("Loading")
        }
     }
     is VideoDetailUiEvent.Success ->{
         VideoDetailScreen(player = player,handleAction, onShowComment = onShowComment)
     }
     else  ->{}
 }
}
@Composable
fun VideoDetailScreen(player: Player, handleAction:(VideoDetailUiState) -> Unit, onShowComment:()->Unit){

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .clickable(onClick = { handleAction(VideoDetailUiState.ToggleVideo) }) )
    {
        val (videoPlayer, sideBar, videoInfo) = createRefs();
        TiktokPlayer(modifier = Modifier.constrainAs(videoPlayer){
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.matchParent
            height= Dimension.matchParent
        }, player = player)
        SideBar(
            modifier = Modifier.constrainAs(sideBar){
                end.linkTo(parent.end, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            },
            onAvtClick = { /*TODO*/ },
            onLikeClick = { /*TODO*/ },
            onChatClick = { /*TODO*/ },
            onSaveClick = { /*TODO*/ },
        onShareClick = {})
        VideoInfoArea(modifier = Modifier.constrainAs(videoInfo){
            start.linkTo(parent.start, margin = 16.dp)
            bottom.linkTo(sideBar.bottom)
            end.linkTo(sideBar.start)
            width = Dimension.fillToConstraints
        }, accountName = "Captain Cold", videoName ="Nó đang hút cái gì thế nhỉ ?"
            , hashTags = mutableListOf("Tín Bịp"), songName = "Molly zoom",)

    }
}
