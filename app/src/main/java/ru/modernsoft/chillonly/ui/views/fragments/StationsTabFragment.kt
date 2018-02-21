package ru.modernsoft.chillonly.ui.views.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.OrderedRealmCollection
import kotlinx.android.synthetic.main.fragment_stations_category.*
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.data.db.StationsFactory
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.ui.adapters.StationAdapter
import ru.modernsoft.chillonly.utils.ViewUtils

class StationsTabFragment : Fragment() {

    private lateinit var adapter: StationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stations_category, container, false)
    }

    override fun onStart() {
        super.onStart()

        val pageNumber = arguments!!.getInt(ARGUMENT_PAGE_NUMBER)
        val list = StationsFactory.getList(pageNumber) // TODO: в презентер
        list?.let { showStations(it) }
    }

    private fun showStations(list: OrderedRealmCollection<Station>) {
        station_list.layoutManager = if (ViewUtils.orientation(activity) == Configuration.ORIENTATION_PORTRAIT)
            LinearLayoutManager(activity)
        else
            GridLayoutManager(activity, 2)

        adapter = StationAdapter(list)
        station_list.adapter = adapter
    }

    companion object {

        private const val ARGUMENT_PAGE_NUMBER = "ARGUMENT_PAGE_NUMBER"

        fun create(page: Int): Fragment {
            val fragment = StationsTabFragment()
            val arguments = Bundle()
            arguments.putInt(ARGUMENT_PAGE_NUMBER, page)
            fragment.arguments = arguments
            return fragment
        }
    }
}
