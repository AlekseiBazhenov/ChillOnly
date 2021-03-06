package ru.modernsoft.chillonly.ui.views.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ru.modernsoft.chillonly.data.Resource
import ru.modernsoft.chillonly.data.Status
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.databinding.FragmentStationsBinding
import ru.modernsoft.chillonly.ui.adapters.StationAdapter
import ru.modernsoft.chillonly.ui.viewmodels.FavoritesViewModel
import ru.modernsoft.chillonly.utils.ViewUtils

class FavoritesFragment : Fragment(), FavoritesFragmentView {

    private var _binding: FragmentStationsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()

    private lateinit var adapter: StationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavorites().observe(viewLifecycleOwner, observer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val observer: (resource: Resource<List<Station>>) -> Unit = {
        when (it.status) {
            Status.LOADING -> {
                binding.progress.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                binding.progress.visibility = View.GONE
                it.data?.let(this@FavoritesFragment::showStations)
            }
            Status.ERROR -> {
                binding.progress.visibility = View.GONE
                // TODO: 31.12.2020 show error
            }
            Status.EMPTY -> {
                // TODO: 31.12.2020 show empty label
            }
        }
    }

    override fun showStations(list: List<Station>) {
        binding.stationsList.layoutManager =
            if (ViewUtils.orientation(activity) == Configuration.ORIENTATION_PORTRAIT)
                LinearLayoutManager(activity)
            else
                GridLayoutManager(activity, 2)

        adapter = StationAdapter(list)
        binding.stationsList.adapter = adapter
    }

    companion object {

        fun create(): Fragment {
            return FavoritesFragment()
        }
    }
}
