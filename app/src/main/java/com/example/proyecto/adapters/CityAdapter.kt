package com.example.proyecto.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.ResenyaResponse
import com.example.proyecto.databinding.ItemCiudadesBinding
import java.util.Locale

class CityAdapter(private val ciudades: List<CityResponse>,private val resenyas: List<ResenyaResponse>?, private val listener: OnClickListenerCities): RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    private lateinit var context: Context

    private var dataList: List<CityResponse> = ciudades


    fun filter(query: String) {
        dataList = if (query.isEmpty()) {
            ciudades
        } else {
            ciudades.filter { it.nombre.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCiudadesBinding.bind(view)

        fun setListener(city: CityResponse){
            binding.root.setOnClickListener {
                listener.onClick(city)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_ciudades, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: CityAdapter.ViewHolder, position: Int) {
        val ciudad: CityResponse? = if (position < dataList.size) {
            dataList[position]
        } else {
            null
        }
        var valoracion: Float = 0.0F
        var contadorResenyas: Int = 0
        with(holder) {
            if (ciudad != null) {
                setListener(ciudad)
            }
            binding.imagenCiudad.setImageResource(R.drawable.bilbao)
            binding.nombreCiudad.text = ciudad?.nombre?.toUpperCase()


                if (resenyas != null) {
                    for (resenya in resenyas) {
                        if (resenya.ciudad.id == ciudad?.id) {
                            println(resenya.valoracion)
                            valoracion += resenya.valoracion.toFloat()
                            contadorResenyas += 1
                        }
                    }
                }

                valoracion /= contadorResenyas.toFloat()
                binding.ratingBar.rating = valoracion
            }


    }

    override fun getItemCount(): Int = ciudades.size


}