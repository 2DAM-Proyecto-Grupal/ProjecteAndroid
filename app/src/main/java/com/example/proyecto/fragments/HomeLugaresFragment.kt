package com.example.proyecto.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyecto.R
import com.example.proyecto.Responses.LugarResponse
import com.example.proyecto.databinding.FragmentHomeLugaresBinding
import com.example.proyecto.databinding.FragmentResenyasLugaresBinding


class HomeLugaresFragment : Fragment() {

    private lateinit var lugar: LugarResponse
    private lateinit var binding: FragmentHomeLugaresBinding

    companion object {
        @JvmStatic
        fun newInstance(l: LugarResponse) =
            HomeLugaresFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("lugar", l)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lugar = it.getSerializable("lugar") as LugarResponse
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeLugaresBinding.inflate(inflater,container,false)

        binding.tvLugarNombre.text = lugar.nombre.toUpperCase()
        binding.tvCiudad.text = "De la ciudad de: ${lugar.ciudad.nombre}"
        binding.tvDireccion.text = "Direccion: ${lugar.direccion}"
        binding.tvInfo.text = "Telefono: ${lugar.informacionContacto}"
        binding.tvTipo.text = "Tipo: ${lugar.tipo}"


        return binding.root
    }


}