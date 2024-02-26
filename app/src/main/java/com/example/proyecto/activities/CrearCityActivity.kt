package com.example.proyecto.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto.API.InterfaceAPI
import com.example.proyecto.R
import com.example.proyecto.Requests.CityRequest
import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.UsuariResponse
import com.example.proyecto.adapters.CityAdapter
import com.example.proyecto.databinding.ActivityCrearCityBinding
import com.example.proyecto.fragments.MainFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CrearCityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrearCityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuario = intent.getSerializableExtra("usuario") as UsuariResponse

        val nombreEditText = binding.nombreEditText
        val paisEditText = binding.pais
        val regionEditText = binding.region
        val jornadaLaboral = binding.jornadaLaboral
        val cashlessSocietyEditText = binding.cashlessSocietyEditText
        val costLiveEditText = binding.costLive
        val costExpaEditText = binding.costExpa
        val costLocEditText = binding.costLoc
        val freeSpeechEditText = binding.freeSpeech
        val healthcareEditText = binding.healthcare
        val internetEditText = binding.internet
        val pazEditText = binding.paz
        val calidadVidaEditText = binding.calidadVida
        val securityEditText = binding.security
        val latitudEditText = binding.latitud
        val longitudEditText = binding.longitud

        val enviarButton = findViewById<Button>(R.id.enviarButton)
        enviarButton.setOnClickListener {
            if (nombreEditText.text.toString().isEmpty() ||
                paisEditText.text.toString().isEmpty() ||
                regionEditText.text.toString().isEmpty() ||
                jornadaLaboral.text.toString().isEmpty() ||
                cashlessSocietyEditText.text.toString().isEmpty() ||
                costLiveEditText.text.toString().isEmpty() ||
                costExpaEditText.text.toString().isEmpty() ||
                costLocEditText.text.toString().isEmpty() ||
                freeSpeechEditText.text.toString().isEmpty() ||
                healthcareEditText.text.toString().isEmpty() ||
                internetEditText.text.toString().isEmpty() ||
                pazEditText.text.toString().isEmpty() ||
                calidadVidaEditText.text.toString().isEmpty() ||
                securityEditText.text.toString().isEmpty() ||
                latitudEditText.text.toString().isEmpty() ||
                longitudEditText.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val city = CityRequest(jornadaLaboral.text.toString(),
                    cashlessSocietyEditText.text.toString().toInt(),
                    costLiveEditText.text.toString().toInt(),
                    costExpaEditText.text.toString().toInt(),
                    costLocEditText.text.toString().toInt(),
                    freeSpeechEditText.text.toString().toInt(),
                    healthcareEditText.text.toString().toInt(),
                    internetEditText.text.toString().toInt(),
                    pazEditText.text.toString().toInt(),
                    calidadVidaEditText.text.toString().toInt(),
                    securityEditText.text.toString().toInt(),
                    regionEditText.text.toString(),
                    paisEditText.text.toString(),
                    nombreEditText.text.toString(),
                    latitudEditText.text.toString().toDouble(),
                    longitudEditText.text.toString().toDouble())

                postCiudad(city)

                Toast.makeText(this, "Formulario enviado", Toast.LENGTH_SHORT).show()

                val intent = Intent(this,MainActivity::class.java)
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

    private fun postCiudad(ciudad: CityRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(InterfaceAPI::class.java).postCiudad(ciudad)

        }
    }
}