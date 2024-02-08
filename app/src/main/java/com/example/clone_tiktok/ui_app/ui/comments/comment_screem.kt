package com.example.clone_tiktok.ui_app.ui.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp


@Composable
fun CommentScreen(modifier: Modifier, videoId: Int, hideBottomSheet: ()->Unit)
{
    val screenHeight = LocalConfiguration.current.screenHeightDp / 2;
Column(modifier = Modifier
    .fillMaxWidth()
    .height(screenHeight.dp)
    .background(Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    , horizontalAlignment = Alignment.CenterHorizontally
    , verticalArrangement = Arrangement.Center) {
Text("Comment for video: $videoId")
    Spacer(modifier = Modifier.height(12.dp))
    Button(onClick = hideBottomSheet) {
        Text(text = "Close")
    }
}
}