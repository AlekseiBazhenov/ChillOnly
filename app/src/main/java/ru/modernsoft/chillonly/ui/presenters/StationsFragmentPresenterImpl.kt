package ru.modernsoft.chillonly.ui.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.modernsoft.chillonly.business.use_cases.GetStationsUseCase
import ru.modernsoft.chillonly.business.use_cases.GetStationsUseCaseImpl
import ru.modernsoft.chillonly.ui.views.fragments.StationsFragmentView

@InjectViewState
class StationsFragmentPresenterImpl : MvpPresenter<StationsFragmentView>(), StationsFragmentPresenter {

    private var interactor: GetStationsUseCase = GetStationsUseCaseImpl()

    override fun onViewStarted(pageNumber: Int) {
        val list = interactor.getStations(pageNumber)
        list?.let { viewState.showStations(it) }
    }
}
