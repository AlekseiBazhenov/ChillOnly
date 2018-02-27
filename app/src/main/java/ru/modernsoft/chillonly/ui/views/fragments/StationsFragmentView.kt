package ru.modernsoft.chillonly.ui.views.fragments

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import io.realm.OrderedRealmCollection
import ru.modernsoft.chillonly.data.models.Station

@StateStrategyType(AddToEndSingleStrategy::class)
//@StateStrategyType(SkipStrategy::class)
interface StationsFragmentView : MvpView {
    fun showStations(list: OrderedRealmCollection<Station>)
}
