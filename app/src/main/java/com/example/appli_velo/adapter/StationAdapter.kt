package com.example.appli_velo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.RecyclerView
import com.example.appli_velo.R
import com.example.appli_velo.api.RetrofitHelper
import com.example.appli_velo.api.StationApi
import com.example.appli_velo.model.Station
import com.example.appli_velo.model.allStations
import com.example.appli_velo.model.currentLocation
import com.example.appli_velo.model.stationSelected
import com.example.appli_velo.ui.home.HomeFragment
import com.example.appli_velo.ui.home.MapsActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.round

class StationAdapter(private val stations:List<Station>, private val ctx : Context, private val framgent: HomeFragment) : RecyclerView.Adapter<StationAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView : CardView = itemView.findViewById(R.id.cardViewPump)
        val name: TextView = itemView.findViewById(R.id.stationName)
        val addresse : TextView = itemView.findViewById(R.id.addresseTextViewPump)
        val status : ImageView = itemView.findViewById(R.id.isOnlineImageView)
        val available : TextView = itemView.findViewById(R.id.availableTextView)
        val distance : TextView = itemView.findViewById(R.id.bikeDistanceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_item, parent, false)
        return ViewHolder(view)
    }

    // pour chaque viewItem on alimente la vue
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var station : Station = stations[position]
        holder.name.text = station.name
        holder.addresse.text = station.adresse
        holder.available.text = "\uD83D\uDEB2${station.availableBikes} \uD83D\uDCE3${station.availableBikeStands} ✅${station.bikeStands}"
        if(station.availableBikes == 0) {
            holder.name.setTextColor(ctx.getColor(R.color.empty_bike))
        } else {
            holder.name.setTextColor(ctx.getColor(R.color.black))
        }
        if(station.status == "OPEN") {
            holder.status.setImageResource(R.drawable.ic_baseline_radio_button_checked_24)
        } else {
            holder.status.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
        }

        holder.status.setOnClickListener {
            framgent.changeStatusStation(station)
        }

        holder.cardView.setOnClickListener {
            val intent : Intent = Intent(ctx, MapsActivity::class.java)
            stationSelected = station
            ctx.startActivity(intent)
        }

        if(currentLocation != null) {
            holder.distance.text = String.format("%.2f", currentLocation!!.distanceTo(station.toLocation()) / 1000) + "KM"
        } else {

        }
    }

    // return stations size
    override fun getItemCount(): Int {
        return stations.size;
    }
}