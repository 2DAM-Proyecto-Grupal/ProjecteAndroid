package com.example.proyecto.Responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponse(@SerializedName("result") val uid: Int, var nombre: String): Serializable
