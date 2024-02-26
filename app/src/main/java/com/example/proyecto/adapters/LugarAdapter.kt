package com.example.proyecto.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.LugarResponse
import com.example.proyecto.databinding.ItemCiudadesBinding
import com.example.proyecto.databinding.ItemLugaresBinding

class LugarAdapter(private val lugares: List<LugarResponse>?, private val listener: OnClickListenerLugares): RecyclerView.Adapter<LugarAdapter.ViewHolder>() {

    private lateinit var context: Context
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemLugaresBinding.bind(view)

        fun setListener(lugar: LugarResponse){
            binding.root.setOnClickListener {
                listener.onClick(lugar)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LugarAdapter.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_lugares, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: LugarAdapter.ViewHolder, position: Int) {
        val lugar = lugares?.get(position)

        with(holder) {
            if (lugar != null) {
                setListener(lugar)
            }
            binding.textViewPlaceName.text = lugar?.nombre
            binding.textViewPlaceAddress.text = lugar?.direccion

        }
    }

    override fun getItemCount(): Int{
        if (lugares != null)
            return lugares.size

        return 0
    }


}