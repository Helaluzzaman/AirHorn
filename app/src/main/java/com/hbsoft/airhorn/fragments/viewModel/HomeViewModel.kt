package com.hbsoft.airhorn.fragments.viewModel

import android.app.Application
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hbsoft.airhorn.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var audioTrack: AudioTrack
    val byte1 = ByteArray(82271)
    val byte2 = ByteArray(293)

    init {
//        setAudioTrackDeprecated(application)
        setAudioTrackNew(application)
    }

    fun startAudio() {
        viewModelScope.launch(Dispatchers.Default) {
            audioTrack.play()
            audioTrack.write(byte1, 0, byte1.size)
            while (audioTrack.playState == AudioTrack.PLAYSTATE_PLAYING) {
                audioTrack.write(byte2, 0, byte2.size)
            }
        }

    }

    //    fun stopAudio(){
//        audioTrack.stop()
//    }
    fun stopAudio() {
        audioTrack.pause()
        audioTrack.flush()
    }

    fun setAudioTrackDeprecated(application: Application) {
        val audioTrackBufferSize = AudioTrack.getMinBufferSize(
            44100,
            AudioFormat.CHANNEL_IN_LEFT,
            AudioFormat.ENCODING_PCM_16BIT
        )
        audioTrack = AudioTrack(
            AudioManager.STREAM_MUSIC,
            44100,
            AudioFormat.CHANNEL_IN_LEFT,
            AudioFormat.ENCODING_PCM_16BIT,
            audioTrackBufferSize,
            AudioTrack.MODE_STREAM
        )
        val inputStream1 = application.applicationContext.resources.openRawResource(R.raw.intro_a)
        val inputStream2 = application.applicationContext.resources.openRawResource(R.raw.loop_a)
        val cut: Long = 44L
        try {
            inputStream1.skip(cut)
            inputStream1.read(byte1)
            inputStream2.skip(cut)
            inputStream2.read(byte2)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setAudioTrackNew(application: Application) {
        val audioTrackBufferSize = AudioTrack.getMinBufferSize(
            44100,
            AudioFormat.CHANNEL_IN_LEFT,
            AudioFormat.ENCODING_PCM_16BIT
        )

        audioTrack = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(44100)
                    .setChannelMask(AudioFormat.CHANNEL_IN_LEFT)
                    .build()
            )
            .setBufferSizeInBytes(audioTrackBufferSize)
            .setTransferMode(AudioTrack.MODE_STREAM)
            .build()
        val inputStream1 = application.applicationContext.resources.openRawResource(R.raw.intro_a)
        val inputStream2 = application.applicationContext.resources.openRawResource(R.raw.loop_a)
        val cut: Long = 44L
        try {
            inputStream1.skip(cut)
            inputStream1.read(byte1)
            inputStream2.skip(cut)
            inputStream2.read(byte2)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}