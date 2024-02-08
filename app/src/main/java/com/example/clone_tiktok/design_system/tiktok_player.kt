package com.example.clone_tiktok.design_system

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

//  PlayerVideo(Android view)  --> Adapter -->composable // adapter design pattern
@OptIn(UnstableApi::class) @Composable
fun TiktokPlayer(
    modifier: Modifier,
    player: Player
){
AndroidView(factory = {
                      context ->
    PlayerView(context).also {
        playerView -> playerView.player = player;
        playerView.useController = false;
        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL;


    }
}, modifier = modifier)

}