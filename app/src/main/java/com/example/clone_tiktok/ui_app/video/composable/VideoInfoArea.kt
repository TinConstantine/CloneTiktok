package com.example.clone_tiktok.ui_app.video.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun VideoInfoArea(modifier: Modifier, accountName: String, videoName:String, hashTags:List<String>,
                  songName:String) {
    Column(modifier = modifier.wrapContentHeight(), verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start) {
        Text(text = accountName, style = MaterialTheme.typography.h4.copy(color = Color.White), maxLines = 1)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = videoName, style = MaterialTheme.typography.h5.copy(color = Color.White), maxLines = 2)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = hashTags.joinToString { " " }, style = MaterialTheme.typography.h1.copy(color = Color.White), maxLines = 1)
        Text(text = songName, style = MaterialTheme.typography.body1.copy(color = Color.White), maxLines = 1)



    }
}