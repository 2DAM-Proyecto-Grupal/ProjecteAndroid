package com.example.proyecto.Responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UsuariResponse(@SerializedName("name") val nombre: String, @SerializedName("is_premium") val premium:Boolean, @SerializedName("id") val uid:Int, @SerializedName("karma") val karma: Int): Serializable{


    data class UsuariList(@SerializedName("result") val list: List<UsuariResponse>)
}
