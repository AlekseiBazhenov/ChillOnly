package ru.modernsoft.chillonly.ui.views.fragments

import io.realm.OrderedRealmCollection
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.modernsoft.chillonly.data.models.Station

@StateStrategyType(AddToEndSingleStrategy::class)
interface StationsFragmentView : MvpView {
    fun showStations(list: OrderedRealmCollection<Station>)
}
