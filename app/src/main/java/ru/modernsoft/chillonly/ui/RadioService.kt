package ru.modernsoft.chillonly.ui

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.modernsoft.chillonly.business.events.PlayerEvent
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.ui.player.ChillMediaPlayer
import ru.modernsoft.chillonly.ui.player.TrackUpdater
import ru.modernsoft.chillonly.ui.views.MainActivity
import timber.log.Timber

class RadioService : Service() {

    private lateinit var chillNotification: ChillNotification // inject
    private lateinit var mediaPlayer: ChillMediaPlayer // inject

    private lateinit var trackUpdater: TrackUpdater // inject

    private var playerUrl = ""

    override fun onCreate() {
        super.onCreate()

        trackUpdater = TrackUpdater.get()
        trackUpdater.provideContext(this)
        chillNotification = ChillNotification(this)
        mediaPlayer = ChillMediaPlayer.get(this)
        mediaPlayer.initMediaPlayer()

        setupBroadcastReceivers()
    }

    private fun setupBroadcastReceivers() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
            playerEventsReceiver,
            IntentFilter(MainActivity.PLAYER_EVENTS_ACTION)
        )

        LocalBroadcastManager.getInstance(this).registerReceiver(
            trackReceiver,
            IntentFilter(TRACK_EVENTS_FILTER)
        )
    }

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val station = getExtraStation(intent)
        playerUrl = station.playerUrl
        chillNotification.createNotification(station.title)

        mediaPlayer.stopPlayerIfPlaying()
        mediaPlayer.startPlayer(station.playerUrl)

        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stopPlayerIfPlaying()
        trackUpdater.stop()

        LocalBroadcastManager.getInstance(this).unregisterReceiver(playerEventsReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(trackReceiver)
    }

    private val playerEventsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val event = intent.getSerializableExtra(MainActivity.PLAYER_EVENT) as PlayerEvent
            Timber.d(event.toString())
            when (event) {
                PlayerEvent.PLAYER_PREPARED -> {
                    trackUpdater.startTrackChecker(playerUrl)
                }
                else -> {
                }
            }
        }
    }

    private val trackReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            var track: String? = null
            if (intent.hasExtra(TRACK_VALUE)) {
                track = intent.getStringExtra(TRACK_VALUE)
            }
            track?.let {
                Timber.d(it)
                chillNotification.updateNotification(it)
            }
        }
    }

    private fun getExtraStation(intent: Intent) =
        intent.getSerializableExtra(EXTRA_STATION) as Station

    companion object {

        const val TRACK_EVENTS_FILTER = "TRACK_EVENTS_FILTER"
        const val TRACK_EVENT = "TRACK_EVENT"
        const val TRACK_VALUE = "TRACK_VALUE"

        const val EXTRA_STATION = "EXTRA_STATION"

        fun start(context: Context, station: Station) {
            val intent = Intent(context, RadioService::class.java)
            intent.putExtra(EXTRA_STATION, station)
            context.startService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, RadioService::class.java)
            context.stopService(intent)
        }
    }
}
