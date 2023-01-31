package com.example.fotozabawa.model

import android.content.Context
import android.media.MediaPlayer
import com.example.fotozabawa.R

class SoundManager(private val context: Context) {
    /*private lateinit var beforePictureSound: MediaPlayer
    private lateinit var afterPictureSound: MediaPlayer
    private lateinit var endSeriesSound: MediaPlayer

    fun initSounds() {
        beforePictureSound = MediaPlayer.create(context, R.raw.notify1)
        afterPictureSound = MediaPlayer.create(context, R.raw.notify2)
        endSeriesSound = MediaPlayer.create(context, R.raw.notify3)
    }*/

    fun playBeforePictureSound() {
        val track: MediaPlayer? = MediaPlayer.create(context, R.raw.notify1) // sound to be configured
        track?.start()
    }

    fun playAfterPictureSound() {
        val track: MediaPlayer? = MediaPlayer.create(context, R.raw.notify2)
        track?.start()
    }

    fun playEndSeriesSound() {
        val track: MediaPlayer? = MediaPlayer.create(context, R.raw.notify3)
        track?.start()
    }
}