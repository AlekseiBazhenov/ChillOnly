package commonMain.kotlin.ru.chillonly.shared.presentation

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.chillonly.shared.Background
import ru.chillonly.shared.GetStationsUseCase
import ru.chillonly.shared.Main

class StationsPresenter(private val view: StationsView) {

    private val case = GetStationsUseCase.CaseProvider.getCase()

    fun start() {
        GlobalScope.apply {
            launch(Background) {
                val json = case.getStations()
                withContext(Main){
                    view.showState(StationsState(json))
                }
            }
        }
    }

}