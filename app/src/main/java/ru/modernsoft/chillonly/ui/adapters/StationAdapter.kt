package ru.modernsoft.chillonly.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_station.view.*
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.business.events.EventType
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.ui.views.MainActivity


class StationAdapter(private val stations: List<Station>)
    : RecyclerView.Adapter<StationAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return stations.size
    }

    override fun getItemId(position: Int): Long {
        return stations[position].id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(stations[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(station: Station) {
            with(station) {
                itemView.name.text = title
                itemView.location.text = description
                itemView.station_layout.setOnClickListener { sendStartPlayerEvent(station) }

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

        private fun sendStartPlayerEvent(id: Station) {
            val intent = Intent(MainActivity.PLAYER_EVENTS_FILTER)
            intent.putExtra(MainActivity.PLAYER_EVENT, EventType.PLAYER_START)
            intent.putExtra(MainActivity.PLAYER_VALUE, id)
            LocalBroadcastManager.getInstance(itemView.context).sendBroadcast(intent)
        }
    }
}
