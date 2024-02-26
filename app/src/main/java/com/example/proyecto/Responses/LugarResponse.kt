package com.example.proyecto.Responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LugarResponse(@SerializedName("id") val id: Int,
                         @SerializedName("nombre") val nombre: String,
                         @SerializedName("tipo") val tipo: String,
                         @SerializedName("direccion") val direccion: String,
                         @SerializedName("informacionContacto") val informacionContacto: String,
                         @SerializedName("ciudad") val ciudad: CityResponse): Serializable
