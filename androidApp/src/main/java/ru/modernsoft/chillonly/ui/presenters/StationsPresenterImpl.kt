package ru.modernsoft.chillonly.ui.presenters

import moxy.InjectViewState
import moxy.MvpPresenter
import ru.modernsoft.chillonly.business.use_cases.LoadStationsUseCase
import ru.modernsoft.chillonly.business.use_cases.LoadStationsUseCaseImpl
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.ui.views.StationsView
import rx.Subscriber

@InjectViewState
class StationsPresenterImpl : MvpPresenter<StationsView>(), StationsPresenter {

    private var useCase: LoadStationsUseCase = LoadStationsUseCaseImpl()
    private lateinit var subscriber: Subscriber<List<Station>>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        useCase.loadStations().subscribe(createSubscriber())
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriber.unsubscribe()
    }

    private fun createSubscriber(): Subscriber<List<Station>> {
        subscriber = object : Subscriber<List<Station>>() {
            override fun onCompleted() {}

            override fun onNext(list: List<Station>) {
                viewState.showStations()
            }

            override fun onError(e: Throwable) {
                viewState.showError(e.message!!)
            }

        }
        return subscriber
    }
}
