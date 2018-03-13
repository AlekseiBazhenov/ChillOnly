package ru.modernsoft.chillonly.ui.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.modernsoft.chillonly.business.interactors.GetStationsInteractor
import ru.modernsoft.chillonly.business.interactors.GetStationsInteractorImpl
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.ui.views.StationsView
import rx.Subscriber

@InjectViewState
class StationsPresenterImpl : MvpPresenter<StationsView>(), StationsPresenter {

    private var interactor: GetStationsInteractor = GetStationsInteractorImpl()
    private lateinit var subscriber: Subscriber<List<Station>>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        interactor.loadStations().subscribe(createSubscriber())
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
