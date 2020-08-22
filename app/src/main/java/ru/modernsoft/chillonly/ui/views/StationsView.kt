package ru.modernsoft.chillonly.ui.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface StationsView : MvpView {
    fun showStations()
    fun showError(message: String)
}
