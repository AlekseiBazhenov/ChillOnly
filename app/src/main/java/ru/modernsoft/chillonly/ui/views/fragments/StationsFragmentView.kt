package ru.modernsoft.chillonly.ui.views.fragments

import ru.modernsoft.chillonly.data.models.Station

interface StationsFragmentView {
    fun showStations(list: List<Station>)
}
