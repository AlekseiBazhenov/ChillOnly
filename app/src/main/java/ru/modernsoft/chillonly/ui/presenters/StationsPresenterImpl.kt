package ru.modernsoft.chillonly.ui.presenters

import ru.modernsoft.chillonly.business.interactors.GetStationsInteractor
import ru.modernsoft.chillonly.business.interactors.GetStationsInteractorImpl
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.ui.views.StationsView
import rx.Subscriber

class StationsPresenterImpl(private val view: StationsView) : StationsPresenter {

    private var interactor: GetStationsInteractor = GetStationsInteractorImpl()
    private lateinit var subscriber: Subscriber<List<Station>>

    override fun onViewStarted() {
        interactor.loadStations().subscribe(createSubscriber())
    }

    override fun onViewStopped() {
        subscriber.unsubscribe()
    }

    private fun createSubscriber(): Subscriber<List<Station>> {
        subscriber = object : Subscriber<List<Station>>() {
            override fun onCompleted() {}

            override fun onNext(list: List<Station>) {
                view.showStations()
            }

            override fun onError(e: Throwable) {
                view.showError(e.message!!)
            }

        }
        return subscriber
    }
}
