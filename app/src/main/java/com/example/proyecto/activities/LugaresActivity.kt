package com.example.proyecto.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.proyecto.R
import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.LugarResponse
import com.example.proyecto.Responses.UsuariResponse
import com.example.proyecto.databinding.ActivityLugaresBinding
import com.example.proyecto.fragments.CityFragment
import com.example.proyecto.fragments.HomeLugaresFragment
import com.example.proyecto.fragments.LugaresFragment
import com.example.proyecto.fragments.ResenyasFragment
import com.example.proyecto.fragments.ResenyasLugaresFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class LugaresActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLugaresBinding
    private lateinit var lugar : LugarResponse
    private lateinit var usuario: UsuariResponse
    private lateinit var ciudad: CityResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLugaresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation = binding.bottomNavigation

        lugar = intent.getSerializableExtra("lugar") as LugarResponse

        ciudad = intent.getSerializableExtra("ciudad") as CityResponse

        usuario = intent.getSerializableExtra("usuario") as UsuariResponse

        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        switchFragment(HomeLugaresFragment.newInstance(lugar))
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bot_home -> {
                    switchFragment(HomeLugaresFragment.newInstance(lugar))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.resenyas -> {
                    switchFragment(ResenyasLugaresFragment.newInstance(lugar, usuario, ciudad))
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contFragment, fragment)
            .commit()
    }
}