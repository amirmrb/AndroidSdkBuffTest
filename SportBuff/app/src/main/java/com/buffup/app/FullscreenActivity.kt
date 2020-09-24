package com.buffup.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_fullscreen.*

class FullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buffView.videoView = video
        video.setVideoPath("https://buffup-public.s3.eu-west-2.amazonaws.com/video/toronto+nba+cut+3.mp4");
        video.setOnPreparedListener {
            video.start()
            buffView.show()
        }
        val m = MutableLiveData<Int>()
        m.observe(this, Observer{})
    }
}
