package com.example.proyecto.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.proyecto.R
import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.UsuariResponse
import com.example.proyecto.databinding.ActivityMapsBinding
import com.example.proyecto.fragments.CityFragment
import com.example.proyecto.fragments.LugaresFragment
import com.example.proyecto.fragments.MainFragment
import com.example.proyecto.fragments.MapsFragment
import com.example.proyecto.fragments.ResenyasFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MapsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapsBinding
    private lateinit var ciudad: CityResponse
    private lateinit var usuario: UsuariResponse

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavigation = findViewById<BottomNavigationView>(
            R.id.bottomNavigation
        )

        ciudad = intent.getSerializableExtra("ciudad") as CityResponse

        usuario = intent.getSerializableExtra("usuario") as UsuariResponse

        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        supportFragmentManager.beginTransaction().replace(binding.containerMaps.id, MapsFragment.newInstance(ciudad)).commit()

        // Mostrar el primer fragment al inicio
        switchFragment(CityFragment.newInstance(ciudad))
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frg, fragment)
            .commit()
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bot_home -> {
                    switchFragment(CityFragment.newInstance(ciudad))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.lugares -> {
                    switchFragment(LugaresFragment.newInstance(ciudad, usuario))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.resenyas -> {
                    switchFragment(ResenyasFragment.newInstance(ciudad))
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }

}