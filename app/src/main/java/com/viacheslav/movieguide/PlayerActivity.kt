package com.viacheslav.movieguide

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView



class PlayerActivity : YouTubeBaseActivity() {
    companion object{
        const val ARG_VIDEO_ID = "video_id"
    }
    private lateinit var videoId: String
    private var youTubePlayer: YouTubePlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        intent.getStringExtra(ARG_VIDEO_ID)?.let { videoId = it }
        findViewById<YouTubePlayerView>(R.id.player).initialize(
            BuildConfig.YOU_TUBE_API_KEY,
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubePlayer?,
                    p2: Boolean
                ) {
                    Log.d("TAG", "onInitializationSuccess: $videoId")
                    youTubePlayer = p1
                    youTubePlayer?.loadVideo(videoId)
                    youTubePlayer?.play()
                }

                override fun onInitializationFailure(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubeInitializationResult?
                ) {
                    Toast.makeText(baseContext, "Error playing ${p1?.name}", Toast.LENGTH_SHORT)
                        .show()
                }
            })

    }
}