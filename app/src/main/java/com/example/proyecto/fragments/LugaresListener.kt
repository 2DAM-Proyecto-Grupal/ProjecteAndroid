package com.example.proyecto.fragments

import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.LugarResponse

interface LugaresListener {

    fun onLugarSeleccionado(l: LugarResponse)

}