package com.example.proyecto.Responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CityResponse(@SerializedName("id") val id: Int,
                        @SerializedName("jornadaLaboral") val jornadaLaboral: String,
                        @SerializedName("cashlessSociety") val cashlessSociety: Int,
                        @SerializedName("costLiving") val costLiving: Int,
                        @SerializedName("costExpat") val costExpat: Int,
                        @SerializedName("costLocal") val costLocal: Int,
                        @SerializedName("freeSpeech") val freeSpeech: Int,
                        @SerializedName("healthcare") val healthcare: Int,
                        @SerializedName("internet") val internet: Int,
                        @SerializedName("peace") val peace: Int,
                        @SerializedName("qualityLife") val qualityLife: Int,
                        @SerializedName("safety") val safety: Int,
                        @SerializedName("region") val region: String,
                        @SerializedName("country") val country: String,
                        @SerializedName("nombre") val nombre: String,
                        @SerializedName("latitude") val latitude: Double,
                        @SerializedName("longitude") val longitude: Double): Serializable{


                        }
