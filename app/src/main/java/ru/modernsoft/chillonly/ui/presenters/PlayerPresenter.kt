package ru.modernsoft.chillonly.ui.presenters

interface PlayerPresenter {
    fun onViewStarted()
    fun onViewStopped()
    fun onAddFavoriteClick()
    fun onChangePlayerStateClick()
}
