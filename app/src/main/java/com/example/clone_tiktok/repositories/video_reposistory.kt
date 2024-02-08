package com.example.clone_tiktok.repositories
import com.example.clone_tiktok.R
import javax.inject.Inject

class VideoRepository @Inject constructor(){
    private  val videos = listOf(
        R.raw.video,
        R.raw.video1,
        R.raw.video2,
        R.raw.video3,
        R.raw.video4,


    )
    fun getVideo()= videos.random();


}