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

    fun playBeforePictureSound(id: Int) {
        val soundId = when (id) {
            1 -> R.raw.notify1
            2 -> R.raw.notify2
            3 -> R.raw.notify3
            4 -> R.raw.notify4
            else -> R.raw.notify1
        }
        val track: MediaPlayer? = MediaPlayer.create(context, soundId)
        track?.start()
    }

    fun playAfterPictureSound(id: Int) {
        val soundId = when (id) {
            1 -> R.raw.notify1
            2 -> R.raw.notify2
            3 -> R.raw.notify3
            4 -> R.raw.notify4
            else -> R.raw.notify2
        }
        val track: MediaPlayer? = MediaPlayer.create(context, soundId)
        track?.start()
    }

    fun playEndSeriesSound(id: Int) {
        val soundId = when (id) {
            1 -> R.raw.notify1
            2 -> R.raw.notify2
            3 -> R.raw.notify3
            4 -> R.raw.notify4
            else -> R.raw.notify3
        }
        val track: MediaPlayer? = MediaPlayer.create(context, soundId)
        track?.start()
    }
}