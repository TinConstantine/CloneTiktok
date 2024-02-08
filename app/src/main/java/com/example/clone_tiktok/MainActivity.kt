package com.example.clone_tiktok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.clone_tiktok.ui.theme.Clone_tiktokTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Clone_tiktokTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    MainScreen()

                }
            }
        }
    }
}


@Composable
fun DemoScaffold() {
    val scaffoldState = rememberScaffoldState();
    val coroutine = rememberCoroutineScope()
    val toggleDrawer = {
        coroutine.launch {
         if(scaffoldState.drawerState.isClosed)
         {
             scaffoldState.drawerState.open()
         }
            else {
             scaffoldState.drawerState.close()
         }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = {Text("Top appbar")}
                , navigationIcon = { IconButton(onClick = {toggleDrawer()}) {
                    Icons.Default.Menu
                }}, actions = {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icons.Default.Search
                        }
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icons.Default.ShoppingCart
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icons.Default.Call
                    }
                }   )
        }, drawerContent = {
            Text(text = "Item 1");
        },
        floatingActionButton = {
            FloatingActionButton(shape = CircleShape,onClick = { /*TODO*/ }) {
                Icons.Default.PlayArrow
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(cutoutShape = CircleShape, modifier = Modifier.clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp ))) {
                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                    val centerVerticalGuide = createGuidelineFromStart(0.5f)
                    val (leftMenu, rightMenu) = createRefs();
                    Row(modifier = Modifier.constrainAs(leftMenu){
                                                                 start.linkTo(parent.start);
                        end.linkTo(centerVerticalGuide, margin = 32.dp)
                        width = Dimension.fillToConstraints
                    },verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = {Icons.Default.Home}, label = { Text(
                            text = "Home"
                        )})
                        BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = {Icons.Default.Home}, label = { Text(
                            text = "Home"
                        )})

                    }
                    Row(modifier = Modifier.constrainAs(rightMenu){
                                                                  start.linkTo(centerVerticalGuide, margin = 32.dp)
                        end.linkTo(parent.end);
                        width = Dimension.fillToConstraints
                    },verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = {Icons.Default.Home}, label = { Text(
                            text = "Home"
                        )})
                        BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = {Icons.Default.Home}, label = { Text(
                            text = "Home"
                        )})}

                    }
                }



        }
    ) {
        paddingValues -> Box(Modifier.padding(paddingValues), contentAlignment = Alignment.Center) {
        Text("Body");}}
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DemoBottomSheetScaffold() {
    val scaffoldState = rememberBottomSheetScaffoldState();
    val coroutine = rememberCoroutineScope();
    val showBottomSheet:()->Unit = {
       coroutine.launch {
           scaffoldState.bottomSheetState.expand();
       }
    }
    val hideBottomSheet:()->Unit = {
        coroutine.launch {
            scaffoldState.bottomSheetState.collapse();
        }
    }
    BottomSheetScaffold(scaffoldState = scaffoldState,sheetContent = {
        hideBottomSheet()
    })  {
        Something(modifier = Modifier) {
            showBottomSheet()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DemoBottomSheetLayout() {
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutine = rememberCoroutineScope()
    val show = {
        coroutine.launch {
            state.show();
        }
    }
    val hidden = {
        coroutine.launch {
            state.hide()
        }
    }
    ModalBottomSheetLayout(sheetContent = {
        BodyDemo(modifier = Modifier) {
            hidden
        }
    }) {
        Something(modifier = Modifier) {
            show()
        }
    }
}

@Composable
fun BodyDemo(modifier: Modifier,onPress: ()->Unit) {
    Box(modifier = modifier.fillMaxSize()){
        IconButton(onClick = onPress) {
            Icons.Default.Close

        }
    }
}
@Composable
fun Something(modifier: Modifier,onPress: ()->Unit) {
    Box(modifier = modifier.fillMaxSize()){
        IconButton(onClick = onPress) {
            Icons.Default.Menu

        }
    }
}

@Composable
fun BottomSheetM3() {

}