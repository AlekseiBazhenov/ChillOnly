package ru.modernsoft.chillonly.ui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.ui.views.fragments.StationsTabFragment

class StationsPagerAdapter(manager: FragmentManager, private val context: Context) : FragmentPagerAdapter(manager) {

    private val pagesCount = 3

    override fun getItem(position: Int): Fragment {
        return StationsTabFragment.create(position)
    }

    override fun getCount(): Int {
        return pagesCount
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> context.getString(R.string.all_radio_stations)
            1 -> context.getString(R.string.recommended_stations)
            2 -> context.getString(R.string.favorite_stations)
            else -> ""
        }
    }
}