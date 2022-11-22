package com.example.appli_velo.model

import android.location.Location

var stationSelected : Station? = null
var allStations :  List<Station>? = null

data class Station (
    val id: Long,
    val name: String,
    val lattitude: Double,
    val longitude: Double,
    val status: String,
    val bikeStands: Int,
    val availableBikes: Int,
    val availableBikeStands: Int,
    val recordId: String,
    val adresse: String
) {
    fun toLocation() : Location {
        val location = Location("")

        location.latitude = lattitude
        location.longitude = longitude

        return location
    }
}