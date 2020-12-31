package ru.modernsoft.chillonly.ui.views

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.business.events.PlayerEvent
import ru.modernsoft.chillonly.data.Resource
import ru.modernsoft.chillonly.data.Status
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.databinding.ActivityMainBinding
import ru.modernsoft.chillonly.ui.RadioService
import ru.modernsoft.chillonly.ui.player.ChillPlayer
import ru.modernsoft.chillonly.ui.viewmodels.FavoritesViewModel
import ru.modernsoft.chillonly.ui.views.fragments.FavoritesFragment
import ru.modernsoft.chillonly.ui.views.fragments.StationsFragment
import ru.modernsoft.chillonly.utils.ServiceUtils
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    companion object {
        const val PLAYER_EVENTS_ACTION = "PLAYER_EVENTS_ACTION"
        const val PLAYER_EVENT = "PLAYER_EVENT"
        const val PLAYER_VALUE = "PLAYER_VALUE"
    }

    private lateinit var binding: ActivityMainBinding

    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupBroadcastReceivers()

        binding.playerView.setListener(playerListener)

        openFragment(StationsFragment.create())

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.all_atations -> {
                    openFragment(StationsFragment.create())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.favorites -> {
                    openFragment(FavoritesFragment.create())
                    return@setOnNavigationItemSelectedListener true
                }
//                R.id.page_3 -> {
//                    loadFragment(StationsFragment.create())
//                    return@setOnNavigationItemSelectedListener true
//                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    private val playerListener = object : ChillPlayer.PlayerListener {
        override fun onAddFavoriteClick(station: Station) {
            addToFavorites(station)
        }

        override fun onDeleteFromFavoriteClick(station: Station) {
            deleteFromFavorites(station)
        }

        override fun onPlayerControlClick(station: Station) {
            if (isPlaying()) {
                RadioService.stop(this@MainActivity)
            } else {
                RadioService.start(this@MainActivity, station)
            }
        }

        override fun checkIsFavorite(station: Station) {
            checkStationIsFavorite(station)
        }
    }

    private fun checkStationIsFavorite(station: Station) {
        val observer: (resource: Resource<Boolean>) -> Unit = {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { isFavorite -> binding.playerView.setStationFavorite(isFavorite) }
                }
                else -> {
                }
            }
        }

        viewModel.checkIsFavorite(station).observe(this@MainActivity, observer)
    }

    private fun addToFavorites(station: Station) {
        val observer: (resource: Resource<Long>) -> Unit = {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this, "added", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                }
                else -> {
                }
            }
        }
        viewModel.onAddToFavoritesClick(station).observe(this@MainActivity, observer)
    }

    private fun deleteFromFavorites(station: Station) {
        val observer: (resource: Resource<Int>) -> Unit = {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                }
                else -> {
                }
            }
        }
        viewModel.onDeleteFromFavoritesClick(station).observe(this@MainActivity, observer)
    }

    private fun setupBroadcastReceivers() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
            playerEventsReceiver,
            IntentFilter(PLAYER_EVENTS_ACTION)
        )

        LocalBroadcastManager.getInstance(this).registerReceiver(
            trackReceiver,
            IntentFilter(RadioService.TRACK_EVENTS_FILTER)
        )
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(playerEventsReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(trackReceiver)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.search -> showSearchScreen()
//            R.id.rate -> launchMarket()
//            R.id.share -> share()
//        }
//        return super.onOptionsItemSelected(item)
//    }

//    private fun showSearchScreen() {
//        SearchActivity.open(this)
//    }
//
//    private fun launchMarket() {
//        val url = Uri.parse(getAppUrl())
//        startActivity(Intent(Intent.ACTION_VIEW, url))
//    }
//
//    private fun share() {
//        val intent = Intent()
//        intent.action = Intent.ACTION_SEND
//        intent.putExtra(Intent.EXTRA_TEXT, "Join ChillOnly ${getAppUrl()}")
//        intent.type = "text/plain"
//        startActivity(intent)
//    }
//
//    private fun getAppUrl() = "http://play.google.com/store/apps/details?id=$packageName"


    private val playerEventsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val event = intent.getSerializableExtra(PLAYER_EVENT) as PlayerEvent
            var value: Station? = null
            if (intent.hasExtra(PLAYER_VALUE)) {
                value = intent.getSerializableExtra(PLAYER_VALUE) as Station
            }
            changePlayerState(event, value)
        }
    }

    private fun changePlayerState(status: PlayerEvent, value: Station?) {
        Timber.d(status.toString())
        when (status) {
            PlayerEvent.PLAYER_START -> {
                value?.let {
                    binding.playerView.setStation(value)

                    if (isPlaying()) {
                        RadioService.stop(this)
                    }
                    RadioService.start(this, value)
                }
            }
            PlayerEvent.PLAYER_CONNECTING -> {
                binding.playerView.showPlayer()
            }
            PlayerEvent.PLAYER_BUFFERING -> {
                binding.playerView.showBuffering()
            }
            PlayerEvent.PLAYER_ERROR -> {
                binding.playerView.showPlayerError()
            }
            PlayerEvent.PLAYER_STOP -> {
                binding.playerView.showStop()
            }
            else -> {
            }
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
                binding.playerView.showTrack(it)
            }
        }
    }

    private fun isPlaying(): Boolean {
        return ServiceUtils.serviceIsRunning(this, RadioService::class.java)
    }
}
