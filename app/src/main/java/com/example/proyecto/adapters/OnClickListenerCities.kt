package com.example.proyecto.adapters

import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.LugarResponse
import com.example.proyecto.fragments.CityListener

interface OnClickListenerCities {
    fun onClick(city: CityResponse)



    fun setCityListener(listener: CityListener)


}