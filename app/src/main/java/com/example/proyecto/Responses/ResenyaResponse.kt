package com.example.proyecto.Responses

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class ResenyaResponse(@SerializedName("id") val id: Int,
                           @SerializedName("titulo") val titulo: String,
                           @SerializedName("textoResenya") val textoResenya: String,
                           @SerializedName("valoracion") val valoracion: Int,
                           @SerializedName("fechaCreacion") val fechaCreacion: String,
                           @SerializedName("ciudad") val ciudad: CityResponse,
                           @SerializedName("lugar") val lugar: LugarResponse,
                           @SerializedName("uid_usuario") val idUsuario: Int,
                           @SerializedName("nombreUsuario") val nombreUsuario: String)
