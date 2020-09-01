package ru.chillonly.shared

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StationsPresenter(private val view: StationsView) {

    private val case = GetStationsUseCase.CaseProvider.getCase()

    fun start() {
        GlobalScope.apply {
            launch(Background) {
                val json = case.getStations()
                withContext(Main){
                    view.showState(StationsState(json[0].title))
                }
            }
        }
    }

}