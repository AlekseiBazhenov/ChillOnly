package ru.modernsoft.chillonly.ui.presenters

import ru.modernsoft.chillonly.business.interactors.GetStationsFromDbInteractor
import ru.modernsoft.chillonly.business.interactors.GetStationsFromDbInteractorImpl
import ru.modernsoft.chillonly.ui.views.fragments.StationsFragmentView

class StationsFragmentPresenterImpl(private val view: StationsFragmentView) : StationsFragmentPresenter {

    private var interactor: GetStationsFromDbInteractor = GetStationsFromDbInteractorImpl()

    override fun onViewStarted(pageNumber: Int) {
        val list = interactor.loadStations(pageNumber)
        list?.let { view.showStations(it) }
    }
}
