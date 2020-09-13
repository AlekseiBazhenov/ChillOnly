package ru.modernsoft.chillonly.business.player

import android.app.Service
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import ru.modernsoft.chillonly.business.events.EventSender
import ru.modernsoft.chillonly.business.events.EventTypes
import java.io.IOException


class ChillMediaPlayer private constructor(private val context: Service) // todo inject Context
    : MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
    MediaPlayer.OnBufferingUpdateListener {

    companion object {

        private var INSTANCE: ChillMediaPlayer? = null

        fun get(context: Service): ChillMediaPlayer {
            if (INSTANCE == null) {
                INSTANCE = ChillMediaPlayer(context)
            }
            return INSTANCE as ChillMediaPlayer
        }
    }

    private lateinit var mediaPlayer: MediaPlayer

    override fun onPrepared(mp: MediaPlayer) {
        updateUI(EventTypes.START_TRACK_UPDATER)
        mp.start()
    }

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        mp.reset()
        updateUI(EventTypes.PLAYER_ERROR)
        return false
    }

    override fun onBufferingUpdate(mediaPlayer: MediaPlayer, percent: Int) { // ???
//        var p = 0
//        if (percent < 0 || percent > 100) {
//            p = Math.round((Math.abs(percent) - 1) * 100.0 / Integer.MAX_VALUE).toInt()
//        }
//        Timber.d("onBufferingUpdate " + p)
    }

    fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        try {
            mediaPlayer.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                            .build()
                    )
                } else {
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                }
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnErrorListener(this)
        mediaPlayer.setOnBufferingUpdateListener(this)
    }

    fun stopPlayerIfPlaying() {
        if (mediaPlayer.isPlaying) {
            updateUI(EventTypes.PLAYER_STOP)
            mediaPlayer.stop()
            mediaPlayer.reset()
        }
    }

    fun startPlayer(url: String) {
        updateUI(EventTypes.PLAYER_CONNECTING)

        try {
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepareAsync()
            updateUI(EventTypes.PLAYER_BUFFERING)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun updateUI(eventType: EventTypes) {
        EventSender().send(eventType)
    }
}