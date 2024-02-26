package com.example.proyecto.Requests

import com.example.proyecto.Responses.CityResponse
import com.google.gson.annotations.SerializedName

data class LugarRequest(@SerializedName("nombre") val nombre: String,
                        @SerializedName("tipo") val tipo: String,
                        @SerializedName("direccion") val direccion: String,
                        @SerializedName("informacionContacto") val informacionContacto: String,
                        @SerializedName("ciudad") val ciudad: CityResponse
)
