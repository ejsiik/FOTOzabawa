package com.example.fotozabawa.model

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import com.example.fotozabawa.R
class SoundManager(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun playBeforePictureSound(id: Int) {
        Log.d("MediaPlayer", "beforek")
        playSound(getSoundUri(id))
    }

    fun playAfterPictureSound(id: Int) {
        Log.d("MediaPlayer", "afterek")
        playSound(getSoundUri(id))
    }

    fun playEndSeriesSound(id: Int) {
        Log.d("MediaPlayer", "end")
        playSound(getSoundUri(id))
    }

    private fun playSound(soundUri: Uri) {
        mediaPlayer?.stop()

        mediaPlayer?.reset()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(context, soundUri)
            setOnPreparedListener { mp -> mp.start() }
            prepareAsync()
        }
    }

    private fun getSoundUri(id: Int): Uri {
        return when (id) {
            0 -> Uri.parse("android.resource://${context.packageName}/${R.raw.notify1}")
            1 -> Uri.parse("android.resource://${context.packageName}/${R.raw.notify2}")
            2 -> Uri.parse("android.resource://${context.packageName}/${R.raw.notify3}")
            3 -> Uri.parse("android.resource://${context.packageName}/${R.raw.notify4}")
            else -> Uri.parse("android.resource://${context.packageName}/${R.raw.notify1}")
        }
    }

    fun releaseMediaPlayer() {
        mediaPlayer?.reset()
        mediaPlayer?.release()
    }
}
