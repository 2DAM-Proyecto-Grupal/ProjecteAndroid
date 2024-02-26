package com.example.proyecto.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.API.InterfaceAPI
import com.example.proyecto.R
import com.example.proyecto.Responses.LoginResponse
import com.example.proyecto.Responses.UsuariResponse
import com.example.proyecto.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var user: LoginResponse? = null
    private var loginSucces: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(R.style.Theme_ProyectoNomadas)

        binding.btnLogin.setOnClickListener {
            val nombre: String = binding.etNombre.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            loginUser("/api/odoo/login", nombre, password)
            CoroutineScope(Dispatchers.Main).launch {
                kotlinx.coroutines.delay(1000)
                if (loginSucces) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("usuario", user)
                    startActivity(intent)
                    finish()
                }
            }

        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")//Parte fija de la URL, la que no varia
            .addConverterFactory(GsonConverterFactory.create())//Libreria de GsonConverter, convierte JSON a ComicResponse
            .build()
    }

    private fun loginUser(query: String, nombre: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(InterfaceAPI::class.java).login("$query/$nombre/$password")
            user = call.body()
            println(user?.uid)
            runOnUiThread {
                if (call.isSuccessful) { //Si hay contenido dentro
                    if (user?.uid != null) {
                        loginSucces = true
                    } else {
                        loginSucces = false
                    }
                } else {
                }
            }

        }
    }


}