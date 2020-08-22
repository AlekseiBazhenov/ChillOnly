package ru.modernsoft.chillonly.ui.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
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
