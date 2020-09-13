package ru.modernsoft.chillonly.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import commonMain.kotlin.ru.chillonly.shared.network.response.Station
import kotlinx.android.synthetic.main.player_layout.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.chillonly.shared.GetStationsUseCase
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.business.events.EventSender
import ru.modernsoft.chillonly.business.events.EventTypes
import ru.modernsoft.chillonly.business.events.RxEventBus
import ru.modernsoft.chillonly.business.services.RadioService
import ru.modernsoft.chillonly.utils.ServiceUtils
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import timber.log.Timber
import java.util.*

class ChillPlayer : CoordinatorLayout, ChillPlayerView {

    private lateinit var playerBehavior: BottomSheetBehavior<View>

    constructor(context: Context) : super(context) {
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initViews()
    }

    private fun initViews() {
        subscribeOnPlayerStates()

        inflate(context, R.layout.player_layout, this)

        playerBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet))
        playerBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        player_layout.setOnClickListener { openDetails() }
        control_button.setOnClickListener { changeState(station.id) }
//        add_to_fav.setOnClickListener { presenter.onAddFavoriteClick() }
    }

    override fun changeState(stationId: Long) {
        if (isPlaying()) {
            RadioService.stop(context)
        } else {
            RadioService.start(context, stationId)
        }
    }

    override fun startRadio(stationId: Long) {
        if (isPlaying()) {
            RadioService.stop(context)
        }
        RadioService.start(context, stationId)
    }

    override fun showPlayer(station: Station) {
        control_button.show()
        playerBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        station_title.text = station.title
//        add_to_fav.isChecked = station.isFav
        track_name.setText(R.string.connecting)
    }

    override fun showBuffering() {
        control_button.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_pause_black_48dp))
        track_name.setText(R.string.buffering)
    }

    override fun showPlayerError() {
        track_name.setText(R.string.connection_error)
    }

    override fun showStop() {
        control_button.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_play_arrow_black_48dp))
    }

    override fun showTrack(track: String) {
        track_name.text = track
    }

    private fun isPlaying(): Boolean {
        return ServiceUtils.serviceIsRunning(context, RadioService::class.java)
    }

    private fun openDetails() {
        // DetailsActivity?
    }


    ///
    private lateinit var playerStatesSubscription: Subscription
    private var playerStatesSubscriber: Action1<HashMap<String, Any>>? = null
    private lateinit var station: Station

    private fun subscribeOnPlayerStates() {
        playerStatesSubscription = RxEventBus.INSTANCE.events
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(playerStatesSubscriber())
    }

    private fun playerStatesSubscriber(): Action1<HashMap<String, Any>> {
        if (playerStatesSubscriber == null) {
            playerStatesSubscriber = Action1 { data ->
                val event = data[EventSender.EVENT_TYPE] as EventTypes
                val value = data[EventSender.VALUE_IN_EVENT]
                changePlayerView(event, value)
            }
        }
        return playerStatesSubscriber as Action1<HashMap<String, Any>>
    }

    private fun changePlayerView(status: EventTypes, value: Any?) {
        Timber.d(status.toString())
        when (status) {
            EventTypes.PLAYER_START -> {
                val station: Station = value as Station
                getStation(station.id)
                startRadio(station.id)
            }
            EventTypes.PLAYER_CONNECTING -> {
                showPlayer(station)
            }
            EventTypes.PLAYER_BUFFERING -> {
                showBuffering()
            }
            EventTypes.PLAYER_ERROR -> {
                showPlayerError()
            }
            EventTypes.PLAYER_STOP -> {
                showStop()
            }
            EventTypes.TRACK_CHANGED -> {
                val track = value as String
                showTrack(track)
            }
            EventTypes.PLAYER_PREPARED -> {
            }
            EventTypes.START_TRACK_UPDATER -> {
            }
        }
    }

    private fun getStation(id: Long) {
        GlobalScope.apply {
            launch(Dispatchers.Default) {
                val s = GetStationsUseCase.CaseProvider.getCase().getStationById(id)
                withContext(Dispatchers.Main){
                    station = s
                }
            }
        }
    }

}