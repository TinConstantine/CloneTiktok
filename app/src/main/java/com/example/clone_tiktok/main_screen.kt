package com.example.clone_tiktok

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.clone_tiktok.ui_app.ui.comments.CommentScreen
import com.example.clone_tiktok.ui_app.ui.following.FollowingScreen
import com.example.clone_tiktok.ui_app.ui.for_you.ListVideoForYouScreen
import kotlinx.coroutines.launch


import com.example.clone_tiktok.ui_app.user.ProfileScreen
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen() {

    val pageState = rememberPagerState(pageCount = { 3 }, initialPage = 1)
    var isShowTabContent by remember {
        mutableStateOf(true)
    }
    val toggleTabContent = {isShow: Boolean ->
        if(isShowTabContent != isShow){
            isShowTabContent = isShow
        }
    }
    val coroutineScope = rememberCoroutineScope();
    val page: (Boolean)->Unit = {
        val index = if(it) 1 else 0;
       coroutineScope.launch {
           pageState.scrollToPage(page = index);
       }
    }
    LaunchedEffect(key1 =pageState ){
        snapshotFlow { pageState.currentPage }.collect{
            page ->    if(page == 2){
                toggleTabContent(false)
        }
            else{
                toggleTabContent(true);
            }
        }
    }
    var currentVideoId by remember {
        mutableStateOf(1);
    }
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val showCommentBottomSheet: (Int) -> Unit = {
        videoId ->
        currentVideoId = videoId
        coroutineScope.launch {
            sheetState.show()
        }
    }
    val hide = {
        currentVideoId = -1;
        coroutineScope.launch {
            sheetState.hide()
        }
    }
    ModalBottomSheetLayout(sheetState = sheetState,sheetContent = {
        if(currentVideoId!=-1){
            CommentScreen(modifier = Modifier, videoId =currentVideoId ) {
                hide()
            }
        }
        else
        {
            Spacer(modifier = Modifier.height(18.dp))
        }
    }) {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(visible = isShowTabContent) {
                    CustomBottomAppBar(onAddVideo = {}, onOpenHome = {})
                }
            }
        ) { it ->
            ConstraintLayout(modifier = Modifier
                .fillMaxSize()
                .padding(it)) {
                val(tabContent,body) = createRefs();
                HorizontalPager(state = pageState, modifier = Modifier.constrainAs(body){
                    top.linkTo(parent.top);
                    bottom.linkTo(parent.bottom);
                    start.linkTo(parent.start);
                    end.linkTo(parent.end);
                    width = Dimension.fillToConstraints;
                    height = Dimension.fillToConstraints;
                }) {
                        index ->
                    when(index){
                        0 -> FollowingScreen(modifier = Modifier);
                        2 -> ProfileScreen();
                        else -> ListVideoForYouScreen(modifier = Modifier){
                            showCommentBottomSheet(it)
                        }
                    }

                }

                AnimatedVisibility(visible = isShowTabContent) {
                    TabContentView(modifier = Modifier.constrainAs(tabContent){
                        top.linkTo(parent.top);
                        bottom.linkTo(parent.bottom);
                        start.linkTo(parent.start);
                        end.linkTo(parent.end);
                        width = Dimension.fillToConstraints;
                    }, tabSelectedIndex = pageState.currentPage
                        , onSelected ={
                            page(it);
                        } )
                }
            }

        }
    }

    }

@Composable
fun CustomBottomAppBar(
    onOpenHome: ()->Unit,
    onAddVideo: ()-> Unit,
) {
    BottomAppBar(
        backgroundColor = Color.Black,
        contentColor = Color.White
    ) {
        BottomNavigationItem(selected = true, onClick = onOpenHome, icon = {Icon(painterResource(id =  R.drawable.ic_home), contentDescription = "Icon home")}, label = { Text(
            text = "Home"
        )})
        BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = {Icon(painterResource(id =  R.drawable.ic_now), contentDescription = "Icon friend")}, label = { Text(
            text = "Friends"
        )})
        BottomNavigationItem(selected = true, onClick = onAddVideo, icon = {Icon(painterResource(id =  R.drawable.ic_add_video), contentDescription = "Icon add")})
        BottomNavigationItem(selected = true, onClick = { /*TODO*/ }, icon = {Icon(painterResource(id =  R.drawable.ic_inbox), contentDescription = "Icon inbox")}, label = {Text  ("Inbox")})
        BottomNavigationItem(selected = true, onClick = { /*TODO*/ }, icon = {Icon(painterResource(id =  R.drawable.ic_profile), contentDescription = "Icon profile")})
    }
}

@Composable
fun TabContentView(modifier: Modifier, tabSelectedIndex : Int, onSelected: (isForU: Boolean)-> Unit) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val(tabContent, imgSearch) = createRefs();
        Row(modifier = Modifier
            .wrapContentSize()
            .constrainAs(tabContent) {
                top.linkTo(parent.top);
                bottom.linkTo(parent.bottom);
                start.linkTo(parent.start);
                end.linkTo(parent.end);
            }){
            TabContentItemView(modifier = Modifier
                , title = "Following"
                , isSelected = tabSelectedIndex == 0
                , isForU = false
                , onSelected = onSelected);
            Spacer(modifier = Modifier.width(12.dp));
            TabContentItemView(modifier = Modifier
                , title = "For you"
                , isSelected = tabSelectedIndex == 1
                , isForU = true
                , onSelected = onSelected);

        }
        IconButton(onClick = { /*TODO*/ },modifier = Modifier
            .constrainAs(imgSearch) {
                top.linkTo(parent.top);
                bottom.linkTo(parent.bottom);
                end.linkTo(parent.end, margin = 16.dp)
            }
            .size(24.dp)) { Icons.Default.Search
        }
    }
}
@Composable
fun TabContentItemView(modifier: Modifier, title: String, isSelected: Boolean,isForU: Boolean,onSelected: (isForU: Boolean)-> Unit) {
    val alpha = if(isSelected) 1f else 0f

Column(modifier = modifier
    .wrapContentSize()
    .clickable { onSelected(isForU) }
    , verticalArrangement = Arrangement.Center
    , Alignment.CenterHorizontally) {
    Text(text = title, style = MaterialTheme.typography.h6.copy(Color.White.copy(alpha)) )
    Spacer(modifier = Modifier.height(8.dp))
    if(isSelected) Divider(color = Color.White, thickness = 2.dp, modifier = Modifier.width(24.dp))


}
    
}