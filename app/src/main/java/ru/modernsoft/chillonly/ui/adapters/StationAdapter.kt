package ru.modernsoft.chillonly.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.modernsoft.chillonly.business.events.PlayerEvent
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.databinding.ItemStationBinding
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
        val binding = ItemStationBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(stations[position])
    }

    class ViewHolder(private val binding: ItemStationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(station: Station) {
            with(station) {
                binding.name.text = title
                binding.location.text = description
                binding.stationLayout.setOnClickListener { sendStartPlayerEvent(station) }

                val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .skipMemoryCache(false)
                    .centerCrop()

                Glide.with(binding.root.context)
                    .load(image)
                    .apply(requestOptions)
                    .into(binding.logo)
            }
        }

        private fun sendStartPlayerEvent(id: Station) {
            val intent = Intent(MainActivity.PLAYER_EVENTS_FILTER)
            intent.putExtra(MainActivity.PLAYER_EVENT, PlayerEvent.PLAYER_START)
            intent.putExtra(MainActivity.PLAYER_VALUE, id)
            LocalBroadcastManager.getInstance(itemView.context).sendBroadcast(intent)
        }
    }
}
