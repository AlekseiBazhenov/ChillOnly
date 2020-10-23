package ru.modernsoft.chillonly.ui.player

import ru.modernsoft.chillonly.data.models.Station

interface ChillPlayer {
    fun setStation(station: Station)
    fun showPlayer()
    fun showBuffering()
    fun showPlayerError()
    fun showStop()
    fun showTrack(track: String)

    interface PlayerListener {
        fun onAddFavoriteClick()
        fun onPlayerControlClick(station: Station)
    }
}
