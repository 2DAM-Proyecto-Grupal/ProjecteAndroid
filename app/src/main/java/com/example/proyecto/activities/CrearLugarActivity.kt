package com.example.proyecto.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyecto.API.InterfaceAPI
import com.example.proyecto.R
import com.example.proyecto.Requests.CityRequest
import com.example.proyecto.Requests.LugarRequest
import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.UsuariResponse
import com.example.proyecto.databinding.ActivityCrearLugarBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CrearLugarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrearLugarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ciudad = intent.getSerializableExtra("ciudad") as CityResponse
        val usuario = intent.getSerializableExtra("usuario") as UsuariResponse

        val nombre = binding.nombreEditText
        val tipo = binding.tipoEditText
        val direccion = binding.direccionEditText
        val info = binding.informacionEditText

        binding.enviarButton.setOnClickListener {
            if (nombre.text.toString().isEmpty() ||
                tipo.text.toString().isEmpty() ||
                direccion.text.toString().isEmpty() ||
                info.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val lugar = LugarRequest(nombre.text.toString(),
                    tipo.text.toString(),
                    direccion.text.toString(),
                    info.text.toString(),
                    ciudad)

                postLugar(lugar)

                Toast.makeText(this, "Formulario enviado", Toast.LENGTH_SHORT).show()

                val intent = Intent(this,MapsActivity::class.java)
                intent.putExtra("ciudad", ciudad)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
                finish()

            }
        }
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")//Parte fija de la URL, la que no varia
            .addConverterFactory(GsonConverterFactory.create())//Libreria de GsonConverter, convierte JSON a ComicResponse
            .build()
    }

    private fun postLugar(lugar: LugarRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(InterfaceAPI::class.java).postLugar(lugar)

        }
    }
}