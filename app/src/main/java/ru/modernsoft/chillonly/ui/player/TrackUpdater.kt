package ru.modernsoft.chillonly.ui.player

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.modernsoft.chillonly.business.events.UpdaterEvent
import ru.modernsoft.chillonly.ui.RadioService
import timber.log.Timber
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class TrackUpdater private constructor() { // TODO: callback, delete android dependencies

    private val service: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    private var future: ScheduledFuture<*>? = null
    private var trackTitle = ""
    private var currentUrl: String? = null
    private var previousUrl: String? = null
    private var context: Context? = null

    companion object {

        private var INSTANCE = TrackUpdater()

        fun get(): TrackUpdater {
            return INSTANCE
        }
    }

    fun provideContext(context: Context) {
        this.context = context
    }

    fun startTrackChecker(url: String) {
        previousUrl = currentUrl
        currentUrl = url
        if (future != null) {
            future!!.cancel(true)
        }
        future = service.scheduleWithFixedDelay({ check() }, 0, 10, TimeUnit.SECONDS)
    }

    fun stop() {
        if (future != null && !future!!.isCancelled) {
            future!!.cancel(true)
        }
    }

    private fun check() {
        var parsedTrack = ""
        try {
            val url = URL(currentUrl)
            val streaming = StreamParser()
            parsedTrack = streaming.getTrackDetails(url)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        Timber.d("UPDATER TRACK - $parsedTrack")
        if (trackTitle != parsedTrack || previousUrl == currentUrl) {
            previousUrl = ""
            trackTitle = parsedTrack
            sendTrackChangedEvent(parsedTrack)
        }
    }

    private fun sendTrackChangedEvent(parsedTrack: String) {
        val intent = Intent(RadioService.TRACK_EVENTS_FILTER)
        intent.putExtra(RadioService.TRACK_EVENT, UpdaterEvent.TRACK_CHANGED)
        intent.putExtra(RadioService.TRACK_VALUE, parsedTrack)
        context?.let { LocalBroadcastManager.getInstance(it).sendBroadcast(intent) }
    }
}
