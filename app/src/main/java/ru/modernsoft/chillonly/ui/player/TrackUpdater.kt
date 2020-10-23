package ru.modernsoft.chillonly.business.player

import ru.modernsoft.chillonly.business.events.EventSender
import ru.modernsoft.chillonly.business.events.EventTypes
import timber.log.Timber
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class TrackUpdater private constructor() {

    private val service: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    private var future: ScheduledFuture<*>? = null
    private var trackTitle = ""
    private var currentUrl: String? = null
    private var previousUrl: String? = null

    companion object {

        private var INSTANCE = TrackUpdater()

        fun get(): TrackUpdater {
            return INSTANCE
        }
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
            EventSender().send(EventTypes.TRACK_CHANGED, parsedTrack)
        }
    }
}
