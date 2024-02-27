package com.example.proyecto.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyecto.R
import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.databinding.FragmentCityBinding


class CityFragment : Fragment() {
    private lateinit var ciudad: CityResponse
    private lateinit var binding: FragmentCityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ciudad = it.getSerializable("ciudad") as CityResponse

        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCityBinding.inflate(inflater, container, false)

        binding.txtNombre.text = ciudad.nombre.toUpperCase()
        binding.txtCountry.text = "Pais:\n ${ciudad.country}"
        binding.txtRegion.text = "Continente:\n ${ciudad.region}"
        binding.prgCash.progress = ciudad.cashlessSociety
        binding.prgCost.progress = ciudad.costLiving
        binding.prgFree.progress = ciudad.freeSpeech
        binding.prgHealth.progress = ciudad.healthcare
        binding.prgInt.progress = ciudad.internet
        binding.prgCostExt.text = ciudad.costExpat.toString() + "$"
        binding.prgCostLoc.text = ciudad.costLocal.toString() + "$"
        binding.prgJornada.text = ciudad.jornadaLaboral
        binding.prgLife.progress = ciudad.qualityLife
        binding.prgPeace.progress = ciudad.peace
        binding.prgSafety.progress = ciudad.safety

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(ciudad: CityResponse) =
            CityFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("ciudad", ciudad)

                }
            }
    }
}