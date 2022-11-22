package com.example.appli_velo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.appli_velo.R
import com.example.appli_velo.model.*
import com.example.appli_velo.ui.home.MapsActivity
import java.text.SimpleDateFormat
import java.util.*

class ParkingAdapter(private val parkings:List<Parking>, private val ctx : Context) : RecyclerView.Adapter<ParkingAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView : CardView = itemView.findViewById(R.id.cardViewParking)
        val nom : TextView = itemView.findViewById(R.id.parkingNameTextView)
        val addresse : TextView = itemView.findViewById(R.id.addresseParkingTextView)
        val capacite : TextView = itemView.findViewById(R.id.capaciteParkingtextView)
        val distance : TextView = itemView.findViewById(R.id.distanceParkingTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_item_parkings, parent, false)
        return ViewHolder(view)
    }

    // pour chaque viewItem on alimente la vue
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parking : Parking = parkings[position]

        var dist : String = ": "
        if(currentLocation != null) {
            dist += String.format("%.2f", currentLocation!!.distanceTo(parking.toLocation()) / 1000) + "KM"
        }

        holder.nom.text = parking.nom
        holder.addresse.text = parking.adresse
        holder.distance.text = dist
        holder.capacite.text = ("🏍: " + parking.motoCapacite + " 🚗:" + parking.voitureCapacite + " 🔌:" + parking.voitureElectriqueCapacite + " 🦽" + parking.pmrCapacite + " 🚴:" + parking.veloCapacite)

        holder.cardView.setOnClickListener {
            val intent : Intent = Intent(ctx, MapsActivity::class.java)
            parkingSelected = parking
            ctx.startActivity(intent)
        }
    }

    // return stations size
    override fun getItemCount(): Int {
        return parkings.size;
    }
}