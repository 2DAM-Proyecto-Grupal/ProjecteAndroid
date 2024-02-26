package com.example.proyecto.fragments

import com.example.proyecto.Responses.CityResponse

interface CityListener {

    fun onCiudadSeleccionada(c: CityResponse)

}