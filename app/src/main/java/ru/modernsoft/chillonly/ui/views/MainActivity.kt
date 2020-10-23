package ru.modernsoft.chillonly.ui.views

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_tabs.*
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.business.events.PlayerEvent
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.ui.RadioService
import ru.modernsoft.chillonly.ui.player.ChillPlayer
import ru.modernsoft.chillonly.ui.views.fragments.StationsFragment
import ru.modernsoft.chillonly.utils.ServiceUtils
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    companion object {
        const val PLAYER_EVENTS_FILTER = "PLAYER_EVENTS_FILTER"
        const val PLAYER_EVENT = "PLAYER_EVENT"
        const val PLAYER_VALUE = "PLAYER_VALUE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        setupBroadcastReceivers()

        playerView.setListener(object : ChillPlayer.PlayerListener {
            override fun onAddFavoriteClick() {
//                presenter.onAddFavoriteClick
            }

            override fun onPlayerControlClick(station: Station) {
                if (isPlaying()) {
                    RadioService.stop(this@MainActivity)
                } else {
                    RadioService.start(this@MainActivity, station)
                }
            }
        })

        val fragment = StationsFragment.create()
        supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
    }

    private fun setupBroadcastReceivers() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
            playerEventsReceiver,
            IntentFilter(PLAYER_EVENTS_FILTER)
        )

        LocalBroadcastManager.getInstance(this).registerReceiver(
            trackReceiver,
            IntentFilter(RadioService.TRACK_EVENTS_FILTER)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(playerEventsReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(trackReceiver)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.search -> showSearchScreen()
            R.id.rate -> launchMarket()
            R.id.share -> share()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSearchScreen() {
        SearchActivity.open(this)
    }

    private fun launchMarket() {
        val url = Uri.parse(getAppUrl())
        startActivity(Intent(Intent.ACTION_VIEW, url))
    }

    private fun share() {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, "Join ChillOnly ${getAppUrl()}")
        intent.type = "text/plain"
        startActivity(intent)
    }

    private fun getAppUrl() = "http://play.google.com/store/apps/details?id=$packageName"


    private val playerEventsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val event = intent.getSerializableExtra(PLAYER_EVENT) as PlayerEvent
            var value: Station? = null
            if (intent.hasExtra(PLAYER_VALUE)) {
                value = intent.getSerializableExtra(PLAYER_VALUE) as Station
            }
            changePlayerView(event, value)
        }
    }

    private fun changePlayerView(status: PlayerEvent, value: Station?) {
        Timber.d(status.toString())
        when (status) {
            PlayerEvent.PLAYER_START -> {
                value?.let {
                    playerView.setStation(value)

                    if (isPlaying()) {
                        RadioService.stop(this)
                    }
                    RadioService.start(this, value)
                }
            }
            PlayerEvent.PLAYER_CONNECTING -> {
                playerView.showPlayer()
            }
            PlayerEvent.PLAYER_BUFFERING -> {
                playerView.showBuffering()
            }
            PlayerEvent.PLAYER_ERROR -> {
                playerView.showPlayerError()
            }
            PlayerEvent.PLAYER_STOP -> {
                playerView.showStop()
            }
            else -> {}
        }
    }

    private val trackReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            var track: String? = null
            if (intent.hasExtra(RadioService.TRACK_VALUE)) {
                track = intent.getStringExtra(RadioService.TRACK_VALUE)
            }
            track?.let {
                Timber.d(it)
                playerView.showTrack(it)
            }
        }
    }

    private fun isPlaying(): Boolean {
        return ServiceUtils.serviceIsRunning(this, RadioService::class.java)
    }
}
