package ru.modernsoft.chillonly.ui.views.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_stations_category.*
import commonMain.kotlin.ru.chillonly.shared.network.response.Station
import commonMain.kotlin.ru.chillonly.shared.presentation.StationsPresenter
import commonMain.kotlin.ru.chillonly.shared.presentation.StationsState
import commonMain.kotlin.ru.chillonly.shared.presentation.StationsView
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.ui.adapters.StationAdapterNew
import ru.modernsoft.chillonly.utils.ViewUtils
import java.io.Serializable

class StationsTabFragmentNew : Fragment(), StationsView {

    private lateinit var presenter: StationsPresenter

    private lateinit var adapter: StationAdapterNew

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stations_category, container, false)
    }

    override fun onStart() {
        super.onStart()
        presenter = StationsPresenter(this)
        presenter.start()
    }

    override fun showState(state: StationsState) {
        station_list.layoutManager =
            if (ViewUtils.orientation(activity) == Configuration.ORIENTATION_PORTRAIT)
                LinearLayoutManager(activity)
            else
                GridLayoutManager(activity, 2)

        adapter = StationAdapterNew(state.data)
        station_list.adapter = adapter
    }

    companion object {
        fun create(): Fragment {
            return StationsTabFragmentNew()
        }
    }
}
