package ru.modernsoft.chillonly.business.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import ru.modernsoft.chillonly.business.events.EventSender
import ru.modernsoft.chillonly.business.events.EventTypes
import ru.modernsoft.chillonly.business.events.RxEventBus
import ru.modernsoft.chillonly.business.player.ChillMediaPlayer
import ru.modernsoft.chillonly.business.player.TrackUpdater
import ru.modernsoft.chillonly.data.repositories.StationsRepositoryImpl
import ru.modernsoft.chillonly.ui.ChillNotification
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import java.util.*

class RadioService : Service() {

    private var updateTrackSubscriber: Action1<HashMap<String, Any>>? = null
    private lateinit var updateTrackSubscription: Subscription
    private lateinit var chillNotification: ChillNotification // inject
    private lateinit var mediaPlayer: ChillMediaPlayer // inject

    private lateinit var trackUpdater: TrackUpdater // inject
    private var stationId: Long = 0
    private var playerUrl = ""

    override fun onCreate() {
        super.onCreate()

        trackUpdater = TrackUpdater.get()
        chillNotification = ChillNotification(this)
        mediaPlayer = ChillMediaPlayer.get(this)
        mediaPlayer.initMediaPlayer()

        subscribeOnUpdateTrack()
    }

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        stationId = getExtraStationId(intent)
        val station = StationsRepositoryImpl().getStationById(stationId)
        playerUrl = station.playerUrl
        chillNotification.createNotification(station.title)

        mediaPlayer.stopPlayerIfPlaying()
        mediaPlayer.startPlayer(station.playerUrl)

        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stopPlayerIfPlaying()
        if (updateTrackSubscription.isUnsubscribed) {
            updateTrackSubscription.unsubscribe()
            updateTrackSubscriber = null
        }
        trackUpdater.stop()
    }

    private fun subscribeOnUpdateTrack() {
        updateTrackSubscription = RxEventBus.INSTANCE.events
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updateTrackSubscriber())
    }

    private fun updateTrackSubscriber(): Action1<HashMap<String, Any>> {
        if (updateTrackSubscriber == null) {
            updateTrackSubscriber = Action1 { data ->
                val event = data[EventSender.EVENT_TYPE] as EventTypes
                if (event == EventTypes.TRACK_CHANGED) {
                    val track = data[EventSender.VALUE_IN_EVENT] as String
                    chillNotification.updateNotification(track)
                }
                if (event == EventTypes.START_TRACK_UPDATER) {
                    trackUpdater.startTrackChecker(playerUrl)
                }
            }
        }
        return updateTrackSubscriber as Action1<HashMap<String, Any>>
    }

    private fun getExtraStationId(intent: Intent) =
            intent.getLongExtra(EXTRA_STATION_ID, 0)

    companion object {

        const val EXTRA_STATION_ID = "id"

        fun start(context: Context, id: Long) {
            val intent = Intent(context, RadioService::class.java)
            intent.putExtra(EXTRA_STATION_ID, id)
            context.startService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, RadioService::class.java)
            context.stopService(intent)
        }
    }
}
