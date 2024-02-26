package com.example.proyecto.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.proyecto.API.InterfaceAPI
import com.example.proyecto.R
import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.LoginResponse
import com.example.proyecto.Responses.UsuariResponse
import com.example.proyecto.databinding.ActivityMainBinding
import com.example.proyecto.fragments.AboutFragment
import com.example.proyecto.fragments.CityListener
import com.example.proyecto.fragments.MainFragment
import com.example.proyecto.fragments.ProfileFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, CityListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var fragment : MainFragment
    private lateinit var usuario: UsuariResponse


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        val user: LoginResponse = intent.getSerializableExtra("usuario") as LoginResponse

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


        infoLogin("api/odoo/user/${user.uid}")



    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                // Reemplaza el contenido del contenedor con el fragmento de inicio
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, MainFragment.newInstance(usuario))
                    .commit()
            }


            R.id.nav_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.nav_profile -> {
                // Reemplaza el contenido del contenedor con el fragmento del perfil
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, ProfileFragment())
                    .commit()

            }


            R.id.nav_about -> {
                // Reemplaza el contenido del contenedor con el fragmento de "Acerca de"
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, AboutFragment())
                    .commit()

            }

            R.id.compPremium ->{
                comprarPremium("api/odoo/venta/${usuario.uid}")
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
        // Cierra el drawer después de hacer la selección
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCiudadSeleccionada(c: CityResponse) {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("ciudad", c)
        startActivity(intent)
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")//Parte fija de la URL, la que no varia
            .addConverterFactory(GsonConverterFactory.create())//Libreria de GsonConverter, convierte JSON a ComicResponse
            .build()
    }

    private fun infoLogin(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(InterfaceAPI::class.java).infoLogin(query)
            val listUser = call.body()!!
            usuario = listUser.list.get(0)

            println(usuario.nombre)

            withContext(Dispatchers.Main) {
                switchFragment(MainFragment.newInstance(usuario))

                if (usuario.premium){
                    binding.navView.menu.findItem(R.id.compPremium).isVisible = false
                }
            }
        }
    }

    private fun comprarPremium(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(InterfaceAPI::class.java).comprarPremium(query)

        }
    }
}