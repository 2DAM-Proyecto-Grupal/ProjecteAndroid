package com.example.proyecto.Requests

import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.LugarResponse
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class ResenyaRequest(@SerializedName("titulo") val titulo: String,
                          @SerializedName("textoResenya") val textoResenya: String,
                          @SerializedName("valoracion") val valoracion: Int,
                          @SerializedName("fechaCreacion") val fechaCreacion: String,
                          @SerializedName("ciudad") val ciudad: CityResponse,
                          @SerializedName("lugar") val lugar: LugarResponse,
                          @SerializedName("uid_usuario") val idUsuario: Int,
                          @SerializedName("nombreUsuario") val nombreUsuario: String)
