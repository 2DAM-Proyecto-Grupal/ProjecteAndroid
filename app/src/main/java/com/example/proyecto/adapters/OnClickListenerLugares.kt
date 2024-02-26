package com.example.proyecto.adapters

import com.example.proyecto.Responses.LugarResponse
import com.example.proyecto.fragments.CityListener
import com.example.proyecto.fragments.LugaresListener

interface OnClickListenerLugares {

    fun onClick(lugar: LugarResponse)

    fun setLugaresListener(listener: LugaresListener)

}