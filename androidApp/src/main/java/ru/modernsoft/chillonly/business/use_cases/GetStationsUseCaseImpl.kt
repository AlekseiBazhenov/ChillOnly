package ru.modernsoft.chillonly.business.use_cases

import io.realm.OrderedRealmCollection
import ru.modernsoft.chillonly.data.db.StationsFactory
import ru.modernsoft.chillonly.data.models.Station

class GetStationsUseCaseImpl : GetStationsUseCase {

//    private val repo = StationsRepositoryImpl()

    override fun getStations(pageNumber: Int): OrderedRealmCollection<Station>? {
        return StationsFactory.getList(pageNumber)
    }
}
