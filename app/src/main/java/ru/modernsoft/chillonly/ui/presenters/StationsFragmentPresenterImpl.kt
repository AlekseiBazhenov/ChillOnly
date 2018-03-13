package ru.modernsoft.chillonly.ui.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.modernsoft.chillonly.business.interactors.GetStationsFromDbInteractor
import ru.modernsoft.chillonly.business.interactors.GetStationsFromDbInteractorImpl
import ru.modernsoft.chillonly.ui.views.fragments.StationsFragmentView

@InjectViewState
class StationsFragmentPresenterImpl : MvpPresenter<StationsFragmentView>(), StationsFragmentPresenter {

    private var interactor: GetStationsFromDbInteractor = GetStationsFromDbInteractorImpl()

    override fun onViewStarted(pageNumber: Int) {
        val list = interactor.loadStations(pageNumber)
        list?.let { viewState.showStations(it) }
    }
}
