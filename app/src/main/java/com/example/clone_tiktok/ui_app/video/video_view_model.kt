package com.example.clone_tiktok.ui_app.video
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_ALL
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.example.clone_tiktok.repositories.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    val videoPlayer : ExoPlayer,
    private val videoRepository: VideoRepository

) : ViewModel(){

    private val _uiState = MutableStateFlow<VideoDetailUiEvent>(VideoDetailUiEvent.Default);
    val uiState : StateFlow<VideoDetailUiEvent>
    get() = _uiState
init {
    Log.e("Cold","Init");
videoPlayer.repeatMode = REPEAT_MODE_ALL;
    videoPlayer.playWhenReady = true;
    videoPlayer.prepare();
    videoPlayer.volume = 0.0f;

}

    fun  handleAction(state: VideoDetailUiState){
        when(state){
            is VideoDetailUiState.LoadData -> {
                loadVideo()
            }
            is VideoDetailUiState.ToggleVideo->{
                toggleVideo()
            }
        }
    }
    private fun loadVideo(){
        _uiState.value = VideoDetailUiEvent.Loading;
        viewModelScope.launch {

            delay(100);
            val video = videoRepository.getVideo();
            // play
            playVideo(video);
            _uiState.value = VideoDetailUiEvent.Success
        }
    }
    @OptIn(UnstableApi::class) private fun playVideo(videoResourceUrl : Int){
        val uri  = RawResourceDataSource.buildRawResourceUri(videoResourceUrl);
        val mediaItem = MediaItem.fromUri(uri);
        videoPlayer.setMediaItem(mediaItem);
        videoPlayer.play();
    }
    private fun toggleVideo(){
        if(videoPlayer.isLoading){}else if(videoPlayer.isPlaying){
            videoPlayer.pause();
        }else{
            videoPlayer.play();
        }

    }

    public override fun onCleared() {
        super.onCleared()
        videoPlayer.release()
    }


}


sealed interface VideoDetailUiEvent{
    object Default:VideoDetailUiEvent
    object Loading:VideoDetailUiEvent
    object Success:VideoDetailUiEvent
    data class Error(val msg:String):VideoDetailUiEvent
}

sealed interface  VideoDetailUiState{
    object LoadData: VideoDetailUiState
    object ToggleVideo:VideoDetailUiState
}