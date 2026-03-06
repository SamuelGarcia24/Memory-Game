package com.ud.memorygame.utils

import android.content.Context
import android.media.MediaPlayer
import com.ud.memorygame.R

class MusicManager(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun startMusic() {
        stopMusic()
        mediaPlayer = MediaPlayer.create(context, R.raw.menu_music).apply {
            isLooping = true
            start()
        }
    }

    fun stopMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        mediaPlayer = null
    }

    fun pauseMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    fun resumeMusic() {
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.start()
            }
        }
    }
}