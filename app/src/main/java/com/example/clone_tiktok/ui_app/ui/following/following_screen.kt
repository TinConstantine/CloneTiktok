package com.example.clone_tiktok.ui_app.ui.following

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PageSize.Fixed
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.example.clone_tiktok.R
import com.example.clone_tiktok.design_system.TiktokPlayer
import kotlin.math.absoluteValue


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FollowingScreen(modifier: Modifier) {
    val cardWith = (LocalConfiguration.current.screenWidthDp * 2/3) - 24;

    val pageCount = rememberPagerState(pageCount = {10})
   Column(
       verticalArrangement = Arrangement.Top,
       horizontalAlignment = Alignment.CenterHorizontally,modifier = modifier
           .fillMaxSize()
           .background(Color.Black)) {
       Spacer(modifier = Modifier.height(40.dp),)
       Text(text = "Trending Creator", style = MaterialTheme.typography.h4.copy(color = Color.White))
        Spacer(modifier = Modifier.height(12.dp))
       Text(text = "Follow an account to see their latest video here", style = MaterialTheme.typography.body1.copy(color = Color.White))
       Spacer(modifier = Modifier.height(36.dp),)
       HorizontalPager(modifier = Modifier
           .fillMaxWidth()
           .aspectRatio(0.8f)
           ,state = pageCount, pageSize = PageSize.Fixed(cardWith.dp),
           pageSpacing = 12.dp,
           contentPadding = PaddingValues(start = 24.dp)
       ) {
           Card(
               modifier = Modifier
                   .width(cardWith.dp)
                   .aspectRatio(0.7f)
                   .graphicsLayer {
                       // Calculate the absolute offset for the current page from the
                       // scroll position. We use the absolute value which allows us to mirror
                       // any effects for both directions
                       val pageOffset = (
                               (pageCount.currentPage - it) + pageCount
                                   .currentPageOffsetFraction
                               ).absoluteValue

                       // We animate the alpha, between 50% and 100%
                       scaleY = lerp(
                           start = 0.7f,
                           stop = 1f,
                           fraction = 1f - pageOffset.coerceIn(0f, 1f)
                       )
                   }
                   .clip(RoundedCornerShape(16.dp))
           ) {
               CreateCard(modifier = Modifier, name = "Cold", accountName = "@cold", onFollow = { /*TODO*/ }) {

               }
           }
       }
   }
}

@Composable
fun CreateCard(modifier: Modifier, name: String, accountName: String, onFollow:()->Unit, onClose: ()->Unit) {
    // Tạo ra 1 ExoPlayer mới
    val videoPlayer = ExoPlayer.Builder(LocalContext.current).build();
    videoPlayer.repeatMode = Player.REPEAT_MODE_ALL;
    videoPlayer.volume = 0.0f;
    videoPlayer.playWhenReady = true;
    videoPlayer.prepare();
    
    ConstraintLayout(modifier = modifier
        .fillMaxSize()
        .clip(shape = RoundedCornerShape(16.dp))) {
        val (videoIntro, closeButton, imgAvt, tvName, tvAccountName, buttonFollow) = createRefs();
           // Chuyển ExoPlayer sang dạng view
            TiktokPlayer(modifier = Modifier.constrainAs(videoIntro){
                                                                    start.linkTo(parent.start);
                end.linkTo(parent.end);
                top.linkTo(parent.top);
                bottom.linkTo(parent.bottom);
                width = Dimension.fillToConstraints;
                height = Dimension.fillToConstraints;
            }, player = videoPlayer);

            IconButton(onClick = onClose, modifier = Modifier
                .constrainAs(closeButton) {
                    top.linkTo(parent.top, margin = 12.dp);
                    end.linkTo(parent.end, margin = 12.dp)
                }
                .size(16.dp)

            ) {
                Icon(imageVector = Icons.Sharp.Close, contentDescription ="Close icon", tint = Color.White )
            }
            Button(onClick = { /*TODO*/ }, modifier = Modifier
                .constrainAs(buttonFollow) {
                    start.linkTo(parent.start);
                    end.linkTo(parent.end);
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                }
                .padding(horizontal = 56.dp, vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFE94359),
            contentColor = Color.White

            )
            ) {
                Text(text = "Follow", style = MaterialTheme.typography.body1)
            }
        Text(text = name
            , style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
            , modifier = Modifier.constrainAs(tvAccountName){
                bottom.linkTo(buttonFollow.top, margin = 8.dp);
                start.linkTo(parent.start);
                end.linkTo(parent.end);

            })
        Text(text = accountName
            , style = MaterialTheme.typography.subtitle1.copy(Color.White)
            , modifier = Modifier.constrainAs(tvAccountName){
                bottom.linkTo(buttonFollow.top, margin = 8.dp);
                start.linkTo(parent.start);
                end.linkTo(parent.end);

            })
            AvatarFollowing(modifier = Modifier.constrainAs(imgAvt){
                start.linkTo(parent.start);
                end.linkTo(parent.end);
                bottom.linkTo(tvName.top, margin = 8.dp);
                
            });


    }
    val uri = RawResourceDataSource.buildRawResourceUri(R.raw.video1);
    val mediaItem = MediaItem.fromUri(uri);
    videoPlayer.setMediaItem(mediaItem);
    videoPlayer.play();
}

@Composable
fun AvatarFollowing(modifier: Modifier) {
    val avtWidth = LocalConfiguration.current.screenWidthDp * 0.2;
    Image(modifier = modifier
        .size(avtWidth.dp)
        .background(color = Color.White, shape = CircleShape)
        .border(color = Color.White, shape = CircleShape, width = 2.dp)
        .clip(shape = CircleShape)
        ,painter = painterResource(id = R.drawable.ic_dog)
        , contentDescription = "Avatar")
}

