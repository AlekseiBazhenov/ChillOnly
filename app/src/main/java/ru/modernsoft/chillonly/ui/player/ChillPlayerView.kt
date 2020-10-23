package ru.modernsoft.chillonly.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.player_layout.view.*
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.ui.RadioService
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.utils.ServiceUtils

// TODO: 05.10.2020 управление плеером сделать из MainActivity
class ChillPlayer : CoordinatorLayout, ChillPlayerView {

//    lateinit var presenter: PlayerPresenterImpl

    private lateinit var listener: ChillPlayerView.PlayerListener

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
        inflate(context, R.layout.player_layout, this)

        playerBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet))
        playerBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        player_layout.setOnClickListener { openDetails() }
        control_button.setOnClickListener { listener.onChangePlayerStateClick() }
        add_to_fav.setOnClickListener { listener.onAddFavoriteClick() }
    }

//    override fun changeState(stationId: Long) {
    override fun changeState(station: Station) {
        if (isPlaying()) {
            RadioService.stop(context)
        } else {
//            RadioService.start(context, stationId)
            RadioService.start(context, station)
        }
    }

    fun setListener(listener: ChillPlayerView.PlayerListener) {
        this.listener = listener
    }

//    override fun startRadio(stationId: Long) {
    override fun startRadio(station: Station) {
        if (isPlaying()) {
            RadioService.stop(context)
        }
//        RadioService.start(context, stationId)
        RadioService.start(context, station)
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
}