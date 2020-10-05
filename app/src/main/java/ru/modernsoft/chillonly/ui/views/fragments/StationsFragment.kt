package ru.modernsoft.chillonly.ui.views.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_stations.*
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.data.Resource
import ru.modernsoft.chillonly.data.Status
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.ui.adapters.StationAdapter
import ru.modernsoft.chillonly.ui.viewmodels.StationsViewModel
import ru.modernsoft.chillonly.utils.ViewUtils

class StationsFragment : Fragment(), StationsFragmentView {

    private val viewModel: StationsViewModel by viewModels()

    private lateinit var adapter: StationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataObserver = Observer<Resource<List<Station>>> {
            when (it.status) {
                Status.LOADING -> {
                    progress.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    progress.visibility = View.GONE
                    showStations(it.data!!)
                }
                Status.ERROR -> {
                    progress.visibility = View.GONE
                }
            }
        }

        viewModel.getStations().observe(viewLifecycleOwner, dataObserver)
    }

    override fun showStations(list: List<Station>) {
        station_list.layoutManager =
            if (ViewUtils.orientation(activity) == Configuration.ORIENTATION_PORTRAIT)
                LinearLayoutManager(activity)
            else
                GridLayoutManager(activity, 2)

        adapter = StationAdapter(list)
        station_list.adapter = adapter
    }

    companion object {

        fun create(): Fragment {
            return StationsFragment()
        }
    }
}
