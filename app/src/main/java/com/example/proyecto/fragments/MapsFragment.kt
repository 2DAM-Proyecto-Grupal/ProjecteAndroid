package com.example.proyecto.fragments

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.proyecto.R
import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.UsuariResponse

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView

class MapsFragment : Fragment(){
    private lateinit var ciudad: CityResponse

    companion object {
        @JvmStatic
        fun newInstance(c: CityResponse) =
            MapsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("ciudad", c)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ciudad = it.getSerializable("ciudad") as CityResponse
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        val ciudadSelec = LatLng(ciudad.latitude, ciudad.longitude)
        googleMap.addMarker(MarkerOptions().position(ciudadSelec).title(ciudad.nombre))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ciudadSelec, 9f))    }



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

}