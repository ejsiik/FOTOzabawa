package com.example.fotozabawa.model

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import com.example.fotozabawa.R

class SoundManager(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    fun playBeforePictureSound(id: Int) {
        val soundId = when (id) {
            0 -> R.raw.notify1
            1 -> R.raw.notify2
            2 -> R.raw.notify3
            3 -> R.raw.notify4
            else -> R.raw.notify1
        }
        val track: MediaPlayer? = MediaPlayer.create(context, soundId)
        /*track?.start()
        track?.setOnCompletionListener {
            // Release the MediaPlayer object when the sound is finished playing
            track.reset()
            track.release()
        }*/
        /*Log.d("SoundManager", "MediaPlayer created")
        track?.start()
        track?.setOnCompletionListener {
            Log.d("SoundManager", "MediaPlayer released")
            if (it.isPlaying) {
                it.stop()
            }
            it.reset()
            it.release()
        }*/
        mediaPlayer?.reset()
        mediaPlayer = MediaPlayer.create(context, soundId)
        mediaPlayer?.start()
    }

    fun playAfterPictureSound(id: Int) {
        val soundId = when (id) {
            0 -> R.raw.notify1
            1 -> R.raw.notify2
            2 -> R.raw.notify3
            3 -> R.raw.notify4
            else -> R.raw.notify2
        }
        val track: MediaPlayer? = MediaPlayer.create(context, soundId)
        /*track?.start()
        track?.setOnCompletionListener {
            track.reset()
            track.release()
        }*/
        /*Log.d("SoundManager", "MediaPlayer created")
        track?.start()
        track?.setOnCompletionListener {
            Log.d("SoundManager", "MediaPlayer released")
            if (it.isPlaying) {
                it.stop()
            }
            it.reset()
            it.release()
        }*/
        mediaPlayer?.reset()
        mediaPlayer = MediaPlayer.create(context, soundId)
        mediaPlayer?.start()

        mediaPlayer?.start()

    }

    fun playEndSeriesSound(id: Int) {
        val soundId = when (id) {
            0 -> R.raw.notify1
            1 -> R.raw.notify2
            2 -> R.raw.notify3
            3 -> R.raw.notify4
            else -> R.raw.notify3
        }
        val track: MediaPlayer? = MediaPlayer.create(context, soundId)
        /*track?.start()
        track?.setOnCompletionListener {
            track.reset()
            track.release()
        }*/
        /*track?.start()
        Log.d("SoundManager", "MediaPlayer created")
        track?.setOnCompletionListener {
            Log.d("SoundManager", "MediaPlayer released")
            if (it.isPlaying) {
                it.stop()
            }
            it.reset()
            it.release()
        }*/
        mediaPlayer?.reset()
        mediaPlayer = MediaPlayer.create(context, soundId)
        mediaPlayer?.start()
    }
    fun releaseMediaPlayer() {
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}