package ru.modernsoft.chillonly.ui.views

import commonMain.kotlin.ru.chillonly.shared.network.response.Station

interface ChillPlayerView {
    fun startRadio(stationId: Long)
    fun showPlayer(station: Station)
    fun showBuffering()
    fun showPlayerError()
    fun showStop()
    fun showTrack(track: String)
    fun changeState(stationId: Long)
}
