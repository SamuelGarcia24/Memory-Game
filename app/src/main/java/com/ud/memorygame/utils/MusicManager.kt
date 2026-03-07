package com.ud.memorygame.utils

import android.content.Context
import android.media.MediaPlayer
import com.ud.memorygame.R

class MusicManager(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private var currentMusic: Int? = null  // track which music is playing

    // start menu music
    fun startMenuMusic() {
        // only restart if different music or not playing
        if (currentMusic != R.raw.menu_music) {
            stopMusic()
            currentMusic = R.raw.menu_music
            mediaPlayer = MediaPlayer.create(context, R.raw.menu_music).apply {
                isLooping = true
                start()
            }
        }
    }

    // start gameplay music
    fun startGameplayMusic() {
        // only restart if different music or not playing
        if (currentMusic != R.raw.gameplay_music) {
            stopMusic()
            currentMusic = R.raw.gameplay_music
            mediaPlayer = MediaPlayer.create(context, R.raw.gameplay_music).apply {
                isLooping = true
                start()
            }
        }
    }

    // stop music
    fun stopMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        mediaPlayer = null
        currentMusic = null
    }

    // pause music
    fun pauseMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    // resume music
    fun resumeMusic() {
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.start()
            }
        }
    }

    // check if music is playing
    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying == true
    }
}