package com.example.appli_velo.model

import android.location.Location
import java.util.*

var parkingSelected : Parking? = null;
var allParkings : List<Parking>? = null;
data class Parking (
    val id: Long,
    val nom: String,
    val voitureElectriqueCapacite: Int,
    val voitureCapacite: Int,
    val veloCapacite: Int,
    val motoCapacite: Int,
    val pmrCapacite: Int,
    val accesPMR: String,
    val adresse: String,
    val telephone: String,
    val site: String,
    val payement: String,
    val lattitude: Double,
    val longitude: Double
) {
    fun toLocation() : Location {
        val location = Location("")

        location.latitude = lattitude
        location.longitude = longitude

        return location
    }
}