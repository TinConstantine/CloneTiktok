package com.example.clone_tiktok.ui_app.video.composable

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.clone_tiktok.R


@Composable
fun SideBar(modifier: Modifier, onAvtClick : ()-> Unit, onLikeClick: ()->Unit, onChatClick:()->Unit,
onSaveClick:()->Unit, onShareClick: ()->Unit)
{
    Column(modifier = modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        AvatarView(modifier = Modifier, onClick = onAvtClick)
        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractive(modifier = Modifier, icon = R.drawable.ic_heart, text = "1.5M", onClick = onLikeClick)
        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractive(modifier = Modifier, icon = R.drawable.ic_chat, text = "8136", onClick = onChatClick)
        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractive(modifier = Modifier, icon = R.drawable.ic_bookmark, text ="90.0K", onClick = onSaveClick)
        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractive(modifier = Modifier, icon = R.drawable.ic_share, text = "75.7K", onClick = onShareClick)
        AudioTrack(modifier = Modifier)
    }
}
@Composable
fun VideoAttractive(modifier: Modifier, @DrawableRes  icon: Int,text: String,onClick: () -> Unit ){
Column(
    modifier = modifier.clickable(onClick = onClick),
    verticalArrangement = Arrangement.Center,
    Alignment.CenterHorizontally
) {
    Icon(painter = painterResource(id = icon)
        , contentDescription = "Icon",
        modifier = Modifier.size(30.dp), tint = Color.White)
    Spacer(modifier = Modifier.height(18.dp))
    Text(text = text, style = MaterialTheme.typography.body2.copy(color = Color.White))
}
}
@Composable
fun AudioTrack(modifier: Modifier)
{
    val infinityTransition = rememberInfiniteTransition();
    val rotate by infinityTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = InfiniteRepeatableSpec(
            repeatMode = RepeatMode.Restart,
            animation = tween(
                durationMillis = 5000,
                easing = LinearEasing
            )
        )
    )
    Image(painter = painterResource(id = R.drawable.ic_audio_track)
        ,contentDescription = "Audio track"
    , modifier = modifier.size(30.dp))
}

@Composable
fun AvatarView(modifier:Modifier, onClick : ()->Unit){
ConstraintLayout(modifier = modifier.clickable (onClick = onClick)) {
    val (imgAvt, addIcon) = createRefs();
    Image(painter = painterResource(id = R.drawable.ic_dog,), contentDescription = "Icon avatar", modifier =
    Modifier
        .size(48.dp)
        .background(shape = CircleShape, color = Color.White)
        .border(width = 2.dp, shape = CircleShape, color = Color.White)
        .clip(shape = CircleShape)
        .constrainAs(imgAvt) {
            top.linkTo(parent.top);
            bottom.linkTo(parent.bottom);
            start.linkTo(parent.start);
            end.linkTo(parent.end);
        });
    Box(
        Modifier
            .size(24.dp)
            .background(color = MaterialTheme.colors.error, shape = CircleShape)
            .constrainAs(addIcon) {
                top.linkTo(imgAvt.bottom);
                bottom.linkTo(imgAvt.bottom);
                start.linkTo(imgAvt.start);
                end.linkTo(imgAvt.end);
            }, contentAlignment = Alignment.Center) {
    Icon(Icons.Default.Add, contentDescription = "Icon add",
        modifier = Modifier.size(12.dp))

    }


    
}
}