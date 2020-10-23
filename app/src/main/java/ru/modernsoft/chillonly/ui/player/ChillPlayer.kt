package ru.modernsoft.chillonly.ui.views

import ru.modernsoft.chillonly.data.models.Station

interface ChillPlayerView {
//    fun startRadio(stationId: Long)
    fun startRadio(station: Station)
    fun showPlayer(station: Station)
    fun showBuffering()
    fun showPlayerError()
    fun showStop()
    fun showTrack(track: String)
//    fun changeState(stationId: Long)
    fun changeState(station: Station)

    interface PlayerListener {
        fun onChangePlayerStateClick()
        fun onAddFavoriteClick()
    }
}
