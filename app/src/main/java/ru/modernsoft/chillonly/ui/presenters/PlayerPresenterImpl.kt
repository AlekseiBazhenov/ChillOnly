package ru.modernsoft.chillonly.ui.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.modernsoft.chillonly.business.events.EventSender
import ru.modernsoft.chillonly.business.events.EventTypes
import ru.modernsoft.chillonly.business.events.RxEventBus
import ru.modernsoft.chillonly.business.interactors.AddStationToFavoritesInteractor
import ru.modernsoft.chillonly.business.interactors.AddStationToFavoritesInteractorImpl
import ru.modernsoft.chillonly.business.interactors.GetStationByIdInteractor
import ru.modernsoft.chillonly.business.interactors.GetStationByIdInteractorImpl
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.ui.views.ChillPlayerView
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import timber.log.Timber
import java.util.*

@InjectViewState
class PlayerPresenterImpl : PlayerPresenter, MvpPresenter<ChillPlayerView>() {

    private var getStationInteractor: GetStationByIdInteractor = GetStationByIdInteractorImpl()
    private var addToFavoritesInteractor: AddStationToFavoritesInteractor = AddStationToFavoritesInteractorImpl()

    private lateinit var playerStatesSubscription: Subscription
    private var playerStatesSubscriber: Action1<HashMap<String, Any>>? = null

    private lateinit var station: Station

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        subscribeOnPlayerStates()
    }

    override fun onDestroy() {
        super.onDestroy()
        playerStatesSubscription.unsubscribe()
    }

    override fun onAddFavoriteClick() {
        addToFavoritesInteractor.addToFavorite(station)
    }

    override fun onChangePlayerStateClick() {
        viewState.changeState(station.id)
    }

    private fun subscribeOnPlayerStates() {
        playerStatesSubscription = RxEventBus.INSTANCE.events
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(playerStatesSubscriber())
    }

    private fun playerStatesSubscriber(): Action1<HashMap<String, Any>> {
        if (playerStatesSubscriber == null) {
            playerStatesSubscriber = Action1 { data ->
                val event = data[EventSender.EVENT_TYPE] as EventTypes
                val value = data[EventSender.VALUE_IN_EVENT]
                changePlayerView(event, value)
            }
        }
        return playerStatesSubscriber as Action1<HashMap<String, Any>>
    }

    private fun changePlayerView(status: EventTypes, value: Any?) {
        Timber.d(status.toString())
        when (status) {
            EventTypes.PLAYER_START -> {
                val stationId: Long = value as Long
                getStation(stationId)
                viewState.startRadio(stationId)
            }
            EventTypes.PLAYER_CONNECTING -> {
                viewState.showPlayer(station)
            }
            EventTypes.PLAYER_BUFFERING -> {
                viewState.showBuffering()
            }
            EventTypes.PLAYER_ERROR -> {
                viewState.showPlayerError()
            }
            EventTypes.PLAYER_STOP -> {
                viewState.showStop()
            }
            EventTypes.TRACK_CHANGED -> {
                val track = value as String
                viewState.showTrack(track)
            }
            EventTypes.PLAYER_PREPARED -> {
            }
            EventTypes.START_TRACK_UPDATER -> {
            }
        }
    }

    private fun getStation(id: Long) {
        station = getStationInteractor.getStation(id)
    }
}
