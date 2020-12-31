package ru.modernsoft.chillonly.ui.player

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.modernsoft.chillonly.business.events.PlayerEvent
import ru.modernsoft.chillonly.ui.views.MainActivity
import java.io.IOException

class ChillMediaPlayer private constructor(private val context: Context) :
    MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
    MediaPlayer.OnBufferingUpdateListener {

    companion object {

        private var INSTANCE: ChillMediaPlayer? = null

        fun get(context: Context): ChillMediaPlayer {
            if (INSTANCE == null) {
                INSTANCE = ChillMediaPlayer(context)
            }
            return INSTANCE as ChillMediaPlayer
        }
    }

    private lateinit var mediaPlayer: MediaPlayer

    override fun onPrepared(mp: MediaPlayer) {
        sendPlayerEvent(PlayerEvent.PLAYER_PREPARED)
        mp.start()
    }

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        mp.reset()
        sendPlayerEvent(PlayerEvent.PLAYER_ERROR)
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
                    @Suppress("DEPRECATION")
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
            sendPlayerEvent(PlayerEvent.PLAYER_STOP)
            mediaPlayer.stop()
            mediaPlayer.reset()
        }
    }

    fun startPlayer(url: String) {
        if (url.isEmpty()) {
            throw IllegalArgumentException("startPlayer(url: String) Empty URL")
        }

        sendPlayerEvent(PlayerEvent.PLAYER_CONNECTING)

        try {
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()
            sendPlayerEvent(PlayerEvent.PLAYER_BUFFERING)
        } catch (e: IllegalStateException) {
            // TODO: 05.10.2020 log crashlytics custom error
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun sendPlayerEvent(event: PlayerEvent) {
        val intent = Intent(MainActivity.PLAYER_EVENTS_ACTION)
        intent.putExtra(MainActivity.PLAYER_EVENT, event)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }
}