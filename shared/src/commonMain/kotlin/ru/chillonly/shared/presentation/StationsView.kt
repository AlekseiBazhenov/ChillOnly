package commonMain.kotlin.ru.chillonly.shared.presentation

import commonMain.kotlin.ru.chillonly.shared.network.response.Station

data class StationsState(val data: List<Station>)

interface StationsView {
    fun showState(state: StationsState)
}