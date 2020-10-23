package ru.modernsoft.chillonly.ui.player

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.databinding.PlayerLayoutBinding

class ChillPlayerView : CoordinatorLayout, ChillPlayer {

    private lateinit var binding: PlayerLayoutBinding

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
        binding = PlayerLayoutBinding.inflate(LayoutInflater.from(context), this, true)

        playerBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        playerBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        binding.playerLayout.setOnClickListener { openDetails() }
        binding.controlButton.setOnClickListener { listener.onPlayerControlClick(station) }
//        add_to_fav.setOnClickListener { listener.onAddFavoriteClick() }
    }

    fun setListener(listener: ChillPlayer.PlayerListener) {
        this.listener = listener
    }

    override fun setStation(station: Station) {
        this.station = station
    }

    override fun showPlayer() {
        binding.controlButton.show()
        playerBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        binding.stationTitle.text = station.title
//        add_to_fav.isChecked = station.isFav
        binding.trackName.setText(R.string.connecting)
    }

    override fun showBuffering() {
        binding.controlButton.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.mipmap.ic_pause_black_48dp
            )
        )
        binding.trackName.setText(R.string.buffering)
    }

    override fun showPlayerError() {
        binding.trackName.setText(R.string.connection_error)
    }

    override fun showStop() {
        binding.controlButton.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.mipmap.ic_play_arrow_black_48dp
            )
        )
    }

    override fun showTrack(track: String) {
        binding.trackName.text = track
    }

    private fun openDetails() {
        // DetailsActivity?
    }
}