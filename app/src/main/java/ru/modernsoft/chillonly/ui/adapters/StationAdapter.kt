package ru.modernsoft.chillonly.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_station.view.*
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.business.events.EventSender
import ru.modernsoft.chillonly.business.events.EventTypes
import ru.modernsoft.chillonly.data.models.Station
import com.bumptech.glide.request.RequestOptions



class StationAdapter(private val stations: OrderedRealmCollection<Station>)
    : RealmRecyclerViewAdapter<Station, StationAdapter.ViewHolder>(stations, true) {

    override fun getItemCount(): Int {
        return stations.size
    }

    override fun getItemId(position: Int): Long {
        return stations[position].id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: StationAdapter.ViewHolder, position: Int) {
        holder.bindView(stations[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(station: Station) {
            with(station) {
                itemView.name.text = title
                itemView.location.text = location
                itemView.station_layout.setOnClickListener { EventSender().send(EventTypes.PLAYER_START, id) }

                val requestOptions = RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .skipMemoryCache(false)
                        .centerCrop()

                Glide.with(itemView.context)
                        .load(image)
                        .apply(requestOptions)
                        .into(itemView.logo)
            }
        }
    }
}
