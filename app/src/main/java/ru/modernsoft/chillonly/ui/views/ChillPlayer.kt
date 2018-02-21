package ru.modernsoft.chillonly.ui.views

import android.content.Context
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.player_layout.view.*
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.business.services.RadioService
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.ui.presenters.PlayerPresenter
import ru.modernsoft.chillonly.ui.presenters.PlayerPresenterImpl
import ru.modernsoft.chillonly.utils.ServiceUtils

class ChillPlayer : CoordinatorLayout, ChillPlayerView {

//    private val BUNDLE_STATE = "BUNDLE_STATE"
//    private val BUNDLE_TRACK = "BUNDLE_TRACK"
//    private val BUNDLE_STATION_ID = "BUNDLE_STATION_ID"

    private lateinit var presenter: PlayerPresenter

    private lateinit var playerBehavior: BottomSheetBehavior<View>

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        inflate(context, R.layout.player_layout, this)

        playerBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet))
        playerBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        presenter = PlayerPresenterImpl(this)

        player_layout.setOnClickListener({ openDetails() })
        control_button.setOnClickListener({ presenter.onChangePlayerStateClick() })
        add_to_fav.setOnClickListener({ presenter.onAddFavoriteClick() })
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.onViewStarted()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.onViewStopped()
    }

    override fun changeState(stationId: Long) {
        if (isPlaying()) {
            RadioService.stop(context)
        } else {
            RadioService.start(context, stationId)
        }
    }

    override fun startRadioService(stationId: Long) {
        if (isPlaying()) {
            RadioService.stop(context)
        }
        RadioService.start(context, stationId)
    }


    override fun showPlayer(station: Station) {
        control_button.show()
        playerBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        station_title.text = station.title
        add_to_fav.isChecked = station.isFav
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

    // todo save presenter. moxy
//    override fun onSaveInstanceState(): Parcelable? {
//        val bundle = Bundle()
//        bundle.putParcelable(BUNDLE_STATE, super.onSaveInstanceState())
//        bundle.putString(BUNDLE_TRACK, track)
//        if (isPlaying()) {
//            bundle.putLong(BUNDLE_STATION_ID, station.id)
//        }
//        return bundle
//    }
//
//    override fun onRestoreInstanceState(state: Parcelable) {
//        var stateCopy: Parcelable? = null
//        if (state is Bundle) {
//            if (isPlaying()) {
//                track = state.getString(BUNDLE_TRACK)
//                track_name.text = track
//                getStation(state.getLong(BUNDLE_STATION_ID))
//                station_title.text = station.title
//                control_button.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_pause_black_48dp))
//            } else {
//                changeState()
//            }
//            stateCopy = state.getParcelable(BUNDLE_STATE)
//        }
//        super.onRestoreInstanceState(if (stateCopy != null) stateCopy else state)
//    }


    private fun isPlaying(): Boolean {
        return ServiceUtils.serviceIsRunning(context, RadioService::class.java)
    }


    private fun openDetails() {
        // DetailsActivity?
    }
}