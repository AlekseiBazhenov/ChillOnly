package ru.modernsoft.chillonly.ui.player

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.player_layout.view.*
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.data.models.Station

class ChillPlayerView : CoordinatorLayout, ChillPlayer {

    private lateinit var station: Station

    private lateinit var listener: ChillPlayer.PlayerListener

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
        control_button.setOnClickListener { listener.onPlayerControlClick(station) }
//        add_to_fav.setOnClickListener { listener.onAddFavoriteClick() }
    }

    fun setListener(listener: ChillPlayer.PlayerListener) {
        this.listener = listener
    }

    override fun setStation(station: Station) {
        this.station = station
    }

    override fun showPlayer() {
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

    private fun openDetails() {
        // DetailsActivity?
    }
}