package ru.modernsoft.chillonly.ui.views.fragments

import io.realm.OrderedRealmCollection
import ru.modernsoft.chillonly.data.models.Station

interface StationsFragmentView {
    fun showStations(list: OrderedRealmCollection<Station>)
}
