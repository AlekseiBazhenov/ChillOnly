package ru.modernsoft.chillonly.ui.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.modernsoft.chillonly.data.models.Station

@StateStrategyType(AddToEndSingleStrategy::class)
interface ChillPlayerView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun startRadio(stationId: Long)
    fun showPlayer(station: Station)
    fun showBuffering()
    fun showPlayerError()
    fun showStop()
    fun showTrack(track: String)
    fun changeState(stationId: Long)
}
