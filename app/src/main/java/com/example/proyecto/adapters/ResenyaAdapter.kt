package com.example.proyecto.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.LugarResponse
import com.example.proyecto.Responses.ResenyaResponse
import com.example.proyecto.databinding.ItemCiudadesBinding
import com.example.proyecto.databinding.ItemLugaresBinding
import com.example.proyecto.databinding.ItemResenyasBinding

class ResenyaAdapter(private val resenyas: List<ResenyaResponse>?): RecyclerView.Adapter<ResenyaAdapter.ViewHolder>() {

    private lateinit var context: Context
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemResenyasBinding.bind(view)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResenyaAdapter.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_resenyas, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ResenyaAdapter.ViewHolder, position: Int) {
        val resenya = resenyas?.get(position)
        println(resenyas?.size)
        with(holder) {
            binding.nombre.text = resenya?.nombreUsuario
            binding.titulo.text = resenya?.titulo
            binding.texto.text = resenya?.textoResenya
            binding.ratingBar.rating = resenya?.valoracion!!.toFloat()

        }
    }

    override fun getItemCount(): Int{
        if (resenyas != null)
            return resenyas.size

        return 0
    }


}