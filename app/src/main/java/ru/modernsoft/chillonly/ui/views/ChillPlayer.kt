package ru.modernsoft.chillonly.ui.views

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.player_layout.view.*
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.business.services.RadioService
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.ui.presenters.PlayerPresenterImpl
import ru.modernsoft.chillonly.utils.ServiceUtils
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.MvpDelegate

class ChillPlayer : CoordinatorLayout, ChillPlayerView {

    private lateinit var mParentDelegate: MvpDelegate<Any>
    private var mMvpDelegate: MvpDelegate<ChillPlayer>? = null

    @InjectPresenter
    lateinit var presenter: PlayerPresenterImpl

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

    fun init(parentDelegate: MvpDelegate<Any>) {
        mParentDelegate = parentDelegate

        getMvpDelegate().onCreate()
        getMvpDelegate().onAttach()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        getMvpDelegate().onSaveInstanceState()
        getMvpDelegate().onDetach()
    }

    private fun getMvpDelegate(): MvpDelegate<ChillPlayer> {
        if (mMvpDelegate != null) {
            return mMvpDelegate as MvpDelegate<ChillPlayer>
        }

        mMvpDelegate = MvpDelegate(this)
        mMvpDelegate!!.setParentDelegate(mParentDelegate, id.toString())
        return mMvpDelegate as MvpDelegate<ChillPlayer>
    }

    private fun initViews() {
        inflate(context, R.layout.player_layout, this)

        playerBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet))
        playerBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        player_layout.setOnClickListener({ openDetails() })
        control_button.setOnClickListener({ presenter.onChangePlayerStateClick() })
        add_to_fav.setOnClickListener({ presenter.onAddFavoriteClick() })
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

    private fun isPlaying(): Boolean {
        return ServiceUtils.serviceIsRunning(context, RadioService::class.java)
    }

    private fun openDetails() {
        // DetailsActivity?
    }
}