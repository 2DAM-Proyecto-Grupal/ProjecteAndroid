package com.example.proyecto.Requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CityRequest(@SerializedName("jornada_laboral") val jornadaLaboral: String,
                       @SerializedName("cashless_society") val cashlessSociety: Int,
                       @SerializedName("cost_of_living") val costLiving: Int,
                       @SerializedName("cost_of_living_for_expat") val costExpat: Int,
                       @SerializedName("cost_of_living_for_local") val costLocal: Int,
                       @SerializedName("freedom_of_speech") val freeSpeech: Int,
                       @SerializedName("healthcare") val healthcare: Int,
                       @SerializedName("internet") val internet: Int,
                       @SerializedName("peace") val peace: Int,
                       @SerializedName("quality_of_life") val qualityLife: Int,
                       @SerializedName("safety") val safety: Int,
                       @SerializedName("region") val region: String,
                       @SerializedName("country") val country: String,
                       @SerializedName("nombre") val nombre: String,
                       @SerializedName("latitude") val latitude: Double,
                       @SerializedName("longitude") val longitude: Double): Serializable{
                       }
